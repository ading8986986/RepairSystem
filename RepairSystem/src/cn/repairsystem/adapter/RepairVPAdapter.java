package cn.repairsystem.adapter;

import java.util.List;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;

public class RepairVPAdapter extends FragmentPagerAdapter{
	
	private List<Fragment> mListViews;
	
	public RepairVPAdapter(FragmentManager fm,List<Fragment> mListViews) {
		super(fm);
	    this.mListViews = mListViews;  
	}		
	
	@Override
	public int getCount() {
		if(null == mListViews) return 0;
		return mListViews.size();
	}

	@Override
	public Fragment getItem(int position) {
		if(null == mListViews) return null;
		return mListViews.get(position);
	}

}
