package com.tedu.lagou.fragment;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.tedu.lagou.R;
import com.tedu.lagou.adapter.CompanyInfoAdapter;
import com.tedu.lagou.bean.CompanyInfo;
import com.tedu.lagou.ui.EmploymentActivity;
import com.tedu.lagou.view.XListView;
import com.tedu.lagou.view.XListView.IXListViewListener;

public class HomeFragment extends BaseFragment implements IXListViewListener {
	@Bind(R.id.lv_lagou_view)
	XListView listView;		//	listview
	List<CompanyInfo> companyInfos = new ArrayList<CompanyInfo>();; // 数据源
	CompanyInfoAdapter adapter;	//适配器
	private Handler mHandler;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_home, container, false);
		ButterKnife.bind(this, view);
//		refresh();
		initListView();
		return view;
	}

	private void initListView() {
		listView.setPullLoadEnable(true);
		adapter = new CompanyInfoAdapter(getActivity(), companyInfos);
		listView.setAdapter(adapter);
		listView.setXListViewListener(this);
		mHandler=new Handler(); 
		
		//listView添加条目单击监听，跳转到UserInfoActivity
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),EmploymentActivity.class);
				intent.putExtra("from", "friend");
				//根据你构建界面的不同而不同
				//我是为ListView添加了一个Header
				//所以在取数据的时候，position-1
				//传送两个参数作为查询条件
				intent.putExtra("companyUser", adapter.getItem(position-1).getC_email());
				intent.putExtra("job", adapter.getItem(position-1).getJob());
				log("job"+adapter.getItem(position-1).getJob());
				log("companyUser"+adapter.getItem(position-1).getC_email());
				log("position->"+position+"id->"+id);
				jump(intent, false);
			}
		});
	}
	

	@Override
	public void onResume() {
		Log.d("tedu", "OnResume");
		refresh();
		super.onResume();
	}

	// 真正获取数据
	private void refresh() {
		Log.d("tedu", "refresh");
		BmobQuery<CompanyInfo> query=new BmobQuery<CompanyInfo>();
		query.order("-createdAt");
		//查询数据数量
//		query.setLimit(3);
		//判断是否有缓存，该方法必须放在查询条件（如果有的话）都设置完之后再来调用才有效，就像这里一样。
		/*boolean isCache = query.hasCachedResult(getActivity(),CompanyInfo.class);
		if(isCache){//--此为举个例子，并不一定按这种方式来设置缓存策略
			//先从缓存读取数据，如果没有，再从网络获取。
		    query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);    // 如果有缓存的话，则设置策略为CACHE_ELSE_NETWORK
		}else{
			//先从网络读取数据，如果没有，再从缓存中获取
		    query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);    // 如果没有缓存的话，则设置策略为NETWORK_ELSE_CACHE
		}*/
		 //执行查询，第一个参数为上下文，第二个参数为查找的回调
		query.findObjects(getActivity(), new FindListener<CompanyInfo>() {
			
			@Override
			public void onSuccess(List<CompanyInfo> list) {
				 Log.d("tedu", "查询到：" + list.size() + "条数据");
				 companyInfos.clear();
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
						companyInfo.setContent(company.getContent());
						Log.d("tedu", "查询到" + "图片地址："+company.getUrl()+"职位:"+company.getJob()+
								"发布时间:"+company.getDate()+"薪资:"+company.getSalary()+"地址:"+
								company.getAddress()+"工作经验:"+company.getWorkTime()+"公司名:"+company.getCompanyName()+
								"要求学历:"+company.getEducation()+"公司类型:"+company.getFinance()+
								"公司人数:"+company.getPeople()+"产业类型:"+company.getIndustry());
						
						companyInfos.add(companyInfo);
					}
//				 companyInfos.addAll(list);
					// 对数据显示在ListView中
				 adapter.notifyDataSetChanged();
				 
			}
			
			@Override
			public void onError(int errcode, String msg) {
				Log.d("tedu", errcode+":"+msg);
				
			}
		});
	 }

	@Override
	public void onRefresh() {
		 mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				companyInfos.clear();
				onResume();
//				adapter.notifyDataSetChanged();
				onLoad();
			}
		}, 2000);
		
	}

	@Override
	public void onLoadMore() {
		  mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				onResume();
				adapter.notifyDataSetChanged();
				onLoad();
				
			}
		}, 2000);
		
	}
	private void onLoad(){
		listView.stopRefresh();
		 listView.stopLoadMore();
		 listView.setRefreshTime(new SimpleDateFormat("HH:mm").format(new Date()));
	}

}
