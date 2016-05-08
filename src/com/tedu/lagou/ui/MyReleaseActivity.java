package com.tedu.lagou.ui;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.tedu.lagou.R;
import com.tedu.lagou.adapter.MyReleaseAdapter;
import com.tedu.lagou.bean.CompanyInfo;
import com.tedu.lagou.view.XListView;
import com.tedu.lagou.view.XListView.IXListViewListener;

import android.os.Bundle;
import android.os.Handler;
/**
 * 
 * @author LWQ
 *我发发布的简历
 */
public class MyReleaseActivity extends BaseActivity implements IXListViewListener {
	@Bind(R.id.lv_lagou_relese)
	XListView listView;		//	listview
	List<CompanyInfo> mcompanyInfos = new ArrayList<CompanyInfo>();; // 数据源
	MyReleaseAdapter madapter;	//适配器
	private Handler mHandler;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_my_release);
		ButterKnife.bind(this);
		//获取数据源
		mcompanyInfos=setData();
		init();
		madapter=new MyReleaseAdapter(this, mcompanyInfos);
		listView.setAdapter(madapter);
		madapter.notifyDataSetChanged();
	}
	
	private void init() {
		listView.setPullLoadEnable(true);
		listView.setXListViewListener(this);
		mHandler=new Handler();
		
	}
	private List<CompanyInfo> setData() {
		log("MyRelesaActivity->setData()");
		mcompanyInfos=new ArrayList<CompanyInfo>();
		String companyUser=getIntent().getStringExtra("name");
		BmobQuery<CompanyInfo> query=new BmobQuery<CompanyInfo>();
		query.order("-createdAt");
		query.addWhereEqualTo("c_email", companyUser);
		query.findObjects(this, new FindListener<CompanyInfo>() {
			
			@Override
			public void onSuccess(List<CompanyInfo> list) {
				if (list.size()>0) {
//					mcompanyInfos.clear();
					for (CompanyInfo company : list) {
						CompanyInfo companyInfo = new CompanyInfo();
						companyInfo.setC_email(company.getC_email());
						companyInfo.setUrl(company.getUrl());
						companyInfo.setJob(company.getJob());
						companyInfo.setDate(company.getDate());
						companyInfo.setCompanyName(company.getCompanyName());
						companyInfo.setSalary(company.getSalary());
						companyInfo.setAddress(company.getAddress());
						companyInfo.setWorkTime(company.getWorkTime());
						companyInfo.setEducation(company.getEducation());
						companyInfo.setFinance(company.getFinance());
						companyInfo.setPeople(company.getPeople());
						companyInfo.setIndustry(company.getIndustry());
						mcompanyInfos.add(companyInfo);
					}
					madapter.notifyDataSetChanged();
				}
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				log("MyReleaseActivity-->获取数据失败");
			}
		});
		return mcompanyInfos;
		
	}
 
	@Override
	public void onRefresh() {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
//				mcompanyInfos.clear();
				mcompanyInfos=setData();
				madapter.notifyDataSetChanged();
				onLoad();
			}

		}, 2000);
		
	}


	@Override
	public void onLoadMore() {
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				mcompanyInfos=setData();
				madapter.notifyDataSetChanged();
				onLoad();
				
			}
		}, 2000);
	}
	private void onLoad() {
		listView.stopRefresh();
		listView.stopLoadMore();
		listView.setRefreshTime(new SimpleDateFormat("HH:mm").format(new Date()));
	}


}
