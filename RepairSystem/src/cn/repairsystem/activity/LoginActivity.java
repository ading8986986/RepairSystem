package cn.repairsystem.activity;

import java.security.MessageDigest;

import org.json.JSONException;
import org.json.JSONObject;

import cn.repairsystem.R;

import cn.repairsystem.RepairSystemApplication;
import cn.repairsystem.network.HttpController;
import cn.repairsystem.network.HttpController.HttpResultListener;
import cn.repairsystem.util.UtilToast;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class LoginActivity extends Activity implements OnClickListener,HttpResultListener{
	
	
	private EditText staffIdEditText;
	private EditText passwordEditText;
	private ImageView savepasswordImageView;
	private TextView titleTextView;
	private Button loginButton;
	private String userid;
	private String userpwd;
	private HttpController mController;
	private LinearLayout waitingLayout;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		initView();
		initData();
	}
	
	private void initData(){
		mController = HttpController.getInstance(this);
	}
	
	private void initView(){
		titleTextView = (TextView) findViewById(R.id.title);
		staffIdEditText = (EditText)findViewById(R.id.staffId_et);
		passwordEditText = (EditText)findViewById(R.id.password_et);
		savepasswordImageView = (ImageView)findViewById(R.id.savepassword);
		loginButton = (Button)findViewById(R.id.login_bn);
		waitingLayout = (LinearLayout) findViewById(R.id.ll_waiting_layout);
		titleTextView .setText("登录");
		if (1 == RepairSystemApplication.savePassword) {
			savepasswordImageView.setSelected(true);
			staffIdEditText.setText(RepairSystemApplication.staffId);
			passwordEditText.setText(RepairSystemApplication.password);
		}
		else {
			savepasswordImageView.setSelected(false);
		}
		loginButton.setOnClickListener(this);
		savepasswordImageView.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.login_bn:
			userid = staffIdEditText.getEditableText().toString();
			userpwd = passwordEditText.getEditableText().toString();
			if(TextUtils.isEmpty(userid) || TextUtils.isEmpty(userpwd)){
				UtilToast.showShort(this, "请输入用户名密码");
			}
			else{
				if(savepasswordImageView.isSelected()){
					SharedPreferences sharedPreferences = getSharedPreferences(cn.repairsystem.util.Constants.SETTINGS,
			                 Context.MODE_PRIVATE);        
					Editor editor = sharedPreferences.edit();
					editor.putInt("savePassword", 1);
					editor.commit();
				}
				else {
					
				}
				waitingLayout.setVisibility(View.VISIBLE);
				login();
			}
			break;
		case R.id.savepassword:
			savepasswordImageView.setSelected(!savepasswordImageView.isSelected());
			break;
		default:
			break;
		}
	}

	@Override
	public void onResult(boolean isSuccess, JSONObject object) {
		// TODO Auto-generated method stub
		waitingLayout.setVisibility(View.GONE);
		if(isSuccess){
			try {
				RepairSystemApplication.sessionId = object.getString("jsessionid");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			Intent intent = new Intent(this,MainActivity.class);
			startActivity(intent);
			finish();
		}
		else {
			if(object != null){
				try {
					UtilToast.showShort(this, object.getString("respDes"));
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	
	private void login(){
		
		userpwd = MD5Encode(userpwd);
		JSONObject object = new JSONObject();
		try {
			object.put("userid", userid);
			object.put("userpwd", userpwd);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mController.login(this, this, object);
	
	}
	
	
	
	
	/**
	 * MD5转码
	 * **/
	public String MD5Encode(String s) {
		char hexDigits[] = { '0', '1', '2', '3', '4',  
                '5', '6', '7', '8', '9',  
                'A', 'B', 'C', 'D', 'E', 'F' };
		try {  
            byte[] btInput = s.getBytes();  
            //获得MD5摘要算法的 MessageDigest 对象  
            MessageDigest mdInst = MessageDigest.getInstance("MD5");  
            //使用指定的字节更新摘要  
            mdInst.update(btInput);  
            //获得密文  
            byte[] md = mdInst.digest();  
            //把密文转换成十六进制的字符串形式  
            int j = md.length;  
            char str[] = new char[j * 2];  
            int k = 0;  
            for (int i = 0; i < j; i++) {  
                byte byte0 = md[i];  
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];  
                str[k++] = hexDigits[byte0 & 0xf];  
            }  
            return new String(str);  
        }  
        catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }
}
