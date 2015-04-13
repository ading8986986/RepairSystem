package cn.repairsystem.activity;

import cn.repairsystem.R;
import cn.repairsystem.R.drawable;
import cn.repairsystem.R.id;
import cn.repairsystem.R.layout;
import cn.repairsystem.fragment.RepairFragment;
import cn.repairsystem.util.UtilToast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class QueDecActivity extends Activity implements OnClickListener {

	private EditText queTitleEditText;
	private EditText queContentEditText;
	private ImageView backImageView;
	private ImageView finishImageView;
	private TextView titleTextView;
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_que_dec);
		initView();
	}
	
	private void initView(){
		backImageView = (ImageView) findViewById(R.id.leftimage);
		finishImageView =(ImageView) findViewById(R.id.rightimage);
		queTitleEditText = (EditText) findViewById(R.id.que_title);
		queContentEditText = (EditText) findViewById(R.id.que_content);
		backImageView.setVisibility(View.VISIBLE);
		finishImageView.setVisibility(View.VISIBLE);
		finishImageView.setBackgroundResource(R.drawable.finish_des);
		finishImageView.setOnClickListener(this);
		backImageView.setOnClickListener(this);
		queTitleEditText.setText(getIntent().getStringExtra("que_title"));
		queContentEditText.setText(getIntent().getStringExtra("que_content"));
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.leftimage:
			finish();
			break;
		case R.id.rightimage:
			if(TextUtils.isEmpty(queTitleEditText.getEditableText().toString().trim())){
				UtilToast.showShort(this, "请输入标题");
				return;
			}
			Intent intent  = new Intent();
			intent.putExtra("que_content", queContentEditText.getEditableText().toString().trim());
			intent.putExtra("que_title", queTitleEditText.getEditableText().toString().trim());
			setResult(RepairFragment.repaireDecTitle_List,intent);
			finish();
			break;
		default:
			break;
		}
	}
}
