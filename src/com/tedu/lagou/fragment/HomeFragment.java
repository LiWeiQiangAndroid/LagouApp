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
	List<CompanyInfo> companyInfos = new ArrayList<CompanyInfo>();; // ����Դ
	CompanyInfoAdapter adapter;	//������
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
		
		//listView�����Ŀ������������ת��UserInfoActivity
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getActivity(),EmploymentActivity.class);
				intent.putExtra("from", "friend");
				//�����㹹������Ĳ�ͬ����ͬ
				//����ΪListView�����һ��Header
				//������ȡ���ݵ�ʱ��position-1
				//��������������Ϊ��ѯ����
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

	// ������ȡ����
	private void refresh() {
		Log.d("tedu", "refresh");
		BmobQuery<CompanyInfo> query=new BmobQuery<CompanyInfo>();
		query.order("-createdAt");
		//��ѯ��������
//		query.setLimit(3);
		//�ж��Ƿ��л��棬�÷���������ڲ�ѯ����������еĻ�����������֮���������ò���Ч����������һ����
		/*boolean isCache = query.hasCachedResult(getActivity(),CompanyInfo.class);
		if(isCache){//--��Ϊ�ٸ����ӣ�����һ�������ַ�ʽ�����û������
			//�ȴӻ����ȡ���ݣ����û�У��ٴ������ȡ��
		    query.setCachePolicy(CachePolicy.CACHE_ELSE_NETWORK);    // ����л���Ļ��������ò���ΪCACHE_ELSE_NETWORK
		}else{
			//�ȴ������ȡ���ݣ����û�У��ٴӻ����л�ȡ
		    query.setCachePolicy(CachePolicy.NETWORK_ELSE_CACHE);    // ���û�л���Ļ��������ò���ΪNETWORK_ELSE_CACHE
		}*/
		 //ִ�в�ѯ����һ������Ϊ�����ģ��ڶ�������Ϊ���ҵĻص�
		query.findObjects(getActivity(), new FindListener<CompanyInfo>() {
			
			@Override
			public void onSuccess(List<CompanyInfo> list) {
				 Log.d("tedu", "��ѯ����" + list.size() + "������");
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
						Log.d("tedu", "��ѯ��" + "ͼƬ��ַ��"+company.getUrl()+"ְλ:"+company.getJob()+
								"����ʱ��:"+company.getDate()+"н��:"+company.getSalary()+"��ַ:"+
								company.getAddress()+"��������:"+company.getWorkTime()+"��˾��:"+company.getCompanyName()+
								"Ҫ��ѧ��:"+company.getEducation()+"��˾����:"+company.getFinance()+
								"��˾����:"+company.getPeople()+"��ҵ����:"+company.getIndustry());
						
						companyInfos.add(companyInfo);
					}
//				 companyInfos.addAll(list);
					// ��������ʾ��ListView��
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
