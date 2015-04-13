package cn.repairsystem.activity;

import cn.repairsystem.R;
import cn.repairsystem.RepairSystemService;
import cn.repairsystem.R.layout;
import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity implements OnClickListener{
	
	private TextView titleTextView;
	private TextView repairTextView;
	private ImageView rightImageView;
	
	//private ServiceConnection serviceConnection;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		initView();
		initData();
		//Intent intent = new Intent(this,RepairSystemService.class);
		//startService(intent);
	}
	
	private void initView(){
		titleTextView = (TextView)findViewById(R.id.title);
		repairTextView = (TextView)findViewById(R.id.repair);
		rightImageView = (ImageView)findViewById(R.id.rightimage);
		
		titleTextView.setText(R.string.title);
		
		rightImageView.setVisibility(View.VISIBLE);
		repairTextView.setOnClickListener(this);
	}
	
	private void initData(){
	
	}
		
	
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		Intent intent = new Intent(this,RepairSystemService.class);
		stopService(intent);
		super.onDestroy();
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.repair:
			Intent intent  = new Intent(this,RepairActivity.class);
			startActivity(intent);
			break;
		case R.id.leftimage:
			finish();
			break;
		default:
			break;
		}
	}
}
