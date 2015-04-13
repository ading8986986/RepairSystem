package cn.repairsystem.activity;

import java.util.ArrayList;

import cn.repairsystem.R;
import cn.repairsystem.RepairSystemApplication;
import cn.repairsystem.adapter.ListItemAdapter;
import cn.repairsystem.bean.RepairBuilding;
import cn.repairsystem.bean.RepairSubProject;
import cn.repairsystem.fragment.RepairFragment;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract.Data;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

public class ListItemActivity extends Activity implements OnClickListener ,OnItemClickListener{
	private ListView listView;
	private TextView titleTextView;
	private ImageView backImageView;
	
	private int listItemType;//展示的是哪个项目的内容
	private String title;
	private ArrayList<String> dataList;
	private ListItemAdapter mAdapter;
	private String titleString;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_list);
		initView();
		initData();
	}
	
	private void initView(){
		listView = (ListView) findViewById(R.id.content);
		titleTextView = (TextView) findViewById(R.id.title);
		backImageView = (ImageView) findViewById(R.id.leftimage);
		backImageView.setOnClickListener(this);
		backImageView.setVisibility(View.VISIBLE);
		listView.setOnItemClickListener(this);
	}
	
	private void initData(){
		mAdapter = new ListItemAdapter(this);
		mAdapter.setList(genDataListAndTitle());
		mAdapter.setSelectedItemPos(getIntent().getIntExtra("setSelectedItemPos", -1));
		titleTextView.setText(titleString);
		listView.setAdapter(mAdapter);
		
	}
	
	private ArrayList<String> genDataListAndTitle(){
		dataList = new ArrayList<String>();
		listItemType = getIntent().getIntExtra("data",0);
		switch (listItemType) {
		case RepairFragment.repaireZone_List:
			for (int i = 0; i < RepairSystemApplication.dataCenter.getRepairZoneList().size(); i++) {
				dataList.add(RepairSystemApplication.dataCenter.getRepairZoneList().get(i).getName());
			}
			titleString = "选择"+getResources().getString(R.string.zone);
			break;
		case RepairFragment.repaireProject_List:
			for (int i = 0; i < RepairSystemApplication.dataCenter.getRepairProjectList().size(); i++) {
				dataList.add(RepairSystemApplication.dataCenter.getRepairProjectList().get(i).name);
			}
			titleString = "选择"+getResources().getString(R.string.project_category);
			break;
		case RepairFragment.repaireBuilding_List:
			ArrayList<RepairBuilding> buildings = RepairSystemApplication.dataCenter.getZoneBuildingById(getIntent().getIntExtra("zone_id", -1));
			if(buildings==null) break;
			for (int i = 0; i < buildings.size(); i++) {
				dataList.add(buildings.get(i).buildname);
			}
			titleString = "选择"+getResources().getString(R.string.building_num);
			break;
		case RepairFragment.repaireSubProject_List:
			ArrayList<RepairSubProject> subProjects = RepairSystemApplication.dataCenter.getRepairSubProjectById(getIntent().getIntExtra("project_id", -1));
			if(subProjects==null) break;
			for (int i = 0; i < subProjects.size(); i++) {
				dataList.add(subProjects.get(i).wxxxmname);
			}
			titleString = "选择"+getResources().getString(R.string.project_subcategory);
			break;
		default:
			break;
		}
		return dataList;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		switch (v.getId()) {
		case R.id.leftimage:
			finish();
			break;

		default:
			break;
		}
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent();
		intent.putExtra("data", dataList.get(position));
		intent.putExtra("setSelectedItemPos", position);
		setResult(listItemType,intent);
		finish();
		
	}
	
	
}
