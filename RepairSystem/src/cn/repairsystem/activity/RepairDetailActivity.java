package cn.repairsystem.activity;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import cn.repairsystem.R;
import cn.repairsystem.R.layout;
import cn.repairsystem.adapter.RepairDetailListAdapter;
import cn.repairsystem.bean.RepairDetailItem;
import cn.repairsystem.bean.RepairDetailListItem;
import cn.repairsystem.network.HttpController;
import cn.repairsystem.util.UtilToast;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

public class RepairDetailActivity extends Activity implements OnClickListener,HttpController.HttpResultListener{

	private ListView listView;
	private ImageView backImageView;
	private TextView titleTextView;
	private LinearLayout waitingLayout;
	private RepairDetailItem detailItem;
	private ArrayList<RepairDetailListItem> datalist;
	
	private RepairDetailListAdapter mAdapter;
	private int repaireId;//报修id
	private HttpController mController;
	private String[] title = new String[]{
			"待审核",
			"待受理",
			"待派工",
			"待完工",
			"维修中",
			"已完工",
			"已回访",
			"已评价",
			"已驳回",
			"已暂停",};
	
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_repair_detail);
		initView();
		initData();
	}
	
	private void initView(){
		listView = (ListView) findViewById(R.id.content);
		backImageView = (ImageView) findViewById(R.id.leftimage);
		backImageView.setVisibility(View.VISIBLE);
		titleTextView = (TextView) findViewById(R.id.title);
		titleTextView.setText(R.string.repair_detail);
		waitingLayout = (LinearLayout) findViewById(R.id.ll_waiting_layout);
		backImageView.setOnClickListener(this);
	}
	
	private void initData(){
		mController = HttpController.getInstance(this);
		repaireId = getIntent().getIntExtra("repaireId", 0);
		mAdapter = new RepairDetailListAdapter(this);
		listView.setAdapter(mAdapter);
		JSONObject param = new JSONObject();
		try {
			param.put("bxid", repaireId);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		waitingLayout.setVisibility(View.VISIBLE);
		mController.getRepairDetail(this, param);
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
	public void onResult(boolean isSuccess, JSONObject object) {
		// TODO Auto-generated method stub
		waitingLayout.setVisibility(View.GONE);
		if(isSuccess&&object!=null){
			int curstate = genDataList(object);
			//if(0<curstate)
			mAdapter.setData(datalist, curstate,detailItem);
		}
		else {
			UtilToast.showShort(this, "获取信息失败");
		}
	}
	
	/**
	 * 生成在list中的datalist
	 * **/
	private int genDataList(JSONObject object){
		datalist = new ArrayList<RepairDetailListItem>();
		try {
			detailItem = new RepairDetailItem(object.getJSONObject("bxinf"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			//return -1;
		}
		if (detailItem.prostatedes<=5) {
				
			for (int i = 1; i <= detailItem.prostatedes; i++) {
				switch (i) {
				case 1:
					datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.bxr+"提交","" ));
					break;
				case 2:
					datalist.add( new RepairDetailListItem(i,title[i-1], "系统分配给受理人"+detailItem.slr,"" ));
					break;
				
				case 3:
					datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.slr+"受理","" ));
					break;
				case 4:
					datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.slr+"指派"+detailItem.wxgname+"维修","" ));
					break;
				case 5:
					datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.wxgname+"维修中","" ));
					break;
				default:
					break;
				}
			}
		}
		else if(detailItem.prostatedes>5 && detailItem.prostatedes<=8){
			for (int i = 1; i <= detailItem.prostatedes; i++) {
				if(i== 5) continue;
				switch (i) {
				case 1:
					datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.bxr+"提交","" ));
					break;
				case 2:
					datalist.add( new RepairDetailListItem(i,title[i-1], "系统分配给受理人"+detailItem.slr,"" ));
					break;
				
				case 3:
					datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.slr+"受理","" ));
					break;
				case 4:
					datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.slr+"指派"+detailItem.wxgname+"维修","" ));
					break;
				case 6:
					datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.wxgname+"维修结束，"+detailItem.wgslr+"录入","" ));
					break;
				case 7:
					datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.hfr+"回访"+detailItem.hffs,"" ));
					break;
				case 8:
					datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.bxr+"评价："+detailItem.pjfs,"" ));
					break;
				default:
					break;
				}
			}
		}
		else if(detailItem.prostatedes>8 && detailItem.prostatedes<=10){
			for (int i = 1; i <= detailItem.prostatedes; i++) {
				if(i>3&&i<9) 
					continue;
				else{
					switch (i) {
					case 1:
						datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.bxr+"提交","" ));
						break;
					case 2:
						datalist.add( new RepairDetailListItem(i,title[i-1], "系统分配给受理人"+detailItem.slr,"" ));
						break;
					
					case 3:
						datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.slr+"受理","" ));
						break;
					
					case 9:
						datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.slr+"驳回","" ));
						break;
					case 10:
						datalist.add( new RepairDetailListItem(i,title[i-1], detailItem.slr+"暂停","" ));
						break;
					default:
						break;
					}
				}
			}
		}
		
		return detailItem.prostatedes;
			
	}
}
