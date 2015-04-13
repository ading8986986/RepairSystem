package cn.repairsystem.activity;

import java.util.ArrayList;
import java.util.List;

import cn.repairsystem.R;
import cn.repairsystem.adapter.RepairVPAdapter;
import cn.repairsystem.fragment.RepairFragment;
import cn.repairsystem.fragment.RepairFragment.SubmitListener;
import cn.repairsystem.fragment.RepairRecordFragment;
import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.TextView;

public class RepairActivity extends FragmentActivity implements OnPageChangeListener, OnClickListener, SubmitListener{
	
	private ViewPager viewPager;
	private TextView titleTextView;
	private ImageView backImageView;
	private TextView repairLable;
	private TextView recordLable;
	private RepairVPAdapter adapterViewpager;
	private Fragment repairFragment,recordFragment;//各个页卡 
	private List<Fragment> mFragments;
	private int mCurPage = 0;// 当前页卡编号
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repair);
		initView();
		
		initViewPager();
	}
	
	private void initView(){
		recordLable = (TextView) findViewById(R.id.record_lable);  
		repairLable = (TextView) findViewById(R.id.repair_lable); 
		titleTextView = (TextView) findViewById(R.id.title);
		titleTextView.setText(R.string.repair);
		backImageView = (ImageView) findViewById(R.id.leftimage);
		backImageView.setVisibility(View.VISIBLE);
		backImageView.setOnClickListener(this);
		recordLable.setOnClickListener(this);
		repairLable.setOnClickListener(this);
	}
	
	
	
	private void initViewPager() {  
	    viewPager=(ViewPager) findViewById(R.id.content);  
	    mFragments=new ArrayList<Fragment>();  
	    repairFragment = new RepairFragment();
	    recordFragment = new RepairRecordFragment();
	    ((RepairFragment)repairFragment).setmListener(this);
	    mFragments.add(repairFragment);  
	    mFragments.add(recordFragment);  
	    adapterViewpager = new RepairVPAdapter(getSupportFragmentManager(),mFragments);
	    viewPager.setAdapter(adapterViewpager);  
	    viewPager.setCurrentItem(0);  
	    viewPager.setOnPageChangeListener(this);  
	    updateTitleSel();	
	}
	
	@SuppressLint("ResourceAsColor")
	private void updateTitleSel(){
		repairLable.setSelected(false);
		recordLable.setSelected(false);
		if (mCurPage == 0) {
			repairLable.setSelected(true);
			repairLable.setTextColor(getResources().getColor(R.color.orange));
			recordLable.setTextColor(getResources().getColor(R.color.gray01));
		} 
		else{
			recordLable.setSelected(true);
			recordLable.setTextColor(getResources().getColor(R.color.orange));
			repairLable.setTextColor(getResources().getColor(R.color.gray01));
		} 
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch(v.getId()){
		case(R.id.repair_lable):
			mCurPage = 0;
			updateTitleSel();	
			viewPager.setCurrentItem(0);
			break;
		case(R.id.record_lable):
			mCurPage = 1;
			updateTitleSel();
			viewPager.setCurrentItem(1);
			break;
		case(R.id.leftimage):
			finish();
			break;
		default:
			break;
	}
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onPageSelected(int position) {
		// TODO Auto-generated method stub
		mCurPage = position;
		updateTitleSel();
		if(position == 1)
			((RepairRecordFragment)recordFragment).getRepairedProjectData();
	}

	@Override
	public void onSubmit() {
		// TODO Auto-generated method stub
		mCurPage = 1;
		updateTitleSel();
		viewPager.setCurrentItem(1);
	}
}
