package com.tedu.lagou.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.tedu.lagou.R;
import com.tedu.lagou.adapter.CompanyInfoAdapter.ViewHolder;
import com.tedu.lagou.bean.CompanyInfo;
import com.tedu.lagou.util.CommonUtil;
import com.tedu.lagou.util.ImageLoader;

public class EmploymentAdapter extends MyBaseAdapter<CompanyInfo> {
	private Context context ;
	List<CompanyInfo> companyInfos;
	LayoutInflater inflater;
	ImageLoader imageLoader;
	
	public EmploymentAdapter(Context context, List<CompanyInfo> datasource) {
		super(context, datasource);
		this.companyInfos=datasource;
		this.context=context;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		imageLoader = new ImageLoader(context.getApplicationContext());
	}
	

	@Override
	public View getItemView(int position, View view, ViewGroup parent) {
		ViewHolder vh;
		if (view == null) {
			view = inflater.inflate(R.layout.layout_item1, parent,
					false);
			vh = new ViewHolder(view);

			view.setTag(vh);
		} else {
			vh = (ViewHolder) view.getTag();
		}

		CompanyInfo companyInfo = getItem(position);
		// logo
		if (TextUtils.isEmpty(companyInfo.getUrl())) {
			vh.iv_lagou_logo.setImageResource(R.drawable.ic_launcher);
		} else {
			imageLoader.DisplayImage(companyInfo.getUrl(), vh.iv_lagou_logo);
		}
		
		vh.tv_lagou_job.setText(companyInfo.getJob());
		// 2016-04-19 16:36:51 companyInfo.getAddTime()
		vh.tv_lagou_companyname.setText(companyInfo.getCompanyName());
		vh.tv_lagou_salary.setText("【"+companyInfo.getSalary()+"】");
		vh.tv_lagou_address.setText(companyInfo.getAddress());
		vh.tv_lagou_workTime.setText(companyInfo.getWorkTime());
		vh.tv_lagou_education.setText(companyInfo.getEducation());
		vh.tv_lagou_finance.setText(companyInfo.getFinance());
		vh.tv_lagou_people.setText(companyInfo.getPeople());
		vh.tv_lagou_industry.setText(companyInfo.getIndustry());
		vh.et_lagou_des.setText(companyInfo.getContent());
		vh.tv_lagou_autor.setText(companyInfo.getC_email());
		Log.d("tedu", "companyInfo.getContent()->"+companyInfo.getContent());

		return view;
	}

	public class ViewHolder {
		@Bind(R.id.iv_lagou_logo)
		ImageView iv_lagou_logo;
		@Bind(R.id.tv_lagou_job)
		TextView tv_lagou_job;
		@Bind(R.id.tv_lagou_companyname)
		TextView tv_lagou_companyname;
		@Bind(R.id.tv_lagou_salary)
		TextView tv_lagou_salary;
		@Bind(R.id.tv_lagou_address)
		TextView tv_lagou_address;
		@Bind(R.id.tv_lagou_workTime)
		TextView tv_lagou_workTime;
		@Bind(R.id.tv_lagou_education)
		TextView tv_lagou_education;
		@Bind(R.id.tv_lagou_finance)
		TextView tv_lagou_finance;
		@Bind(R.id.tv_lagou_people)
		TextView tv_lagou_people;
		@Bind(R.id.tv_lagou_industry)
		TextView tv_lagou_industry;
		
		@Bind(R.id.et_lagou_des)
		TextView et_lagou_des;
		
		@Bind(R.id.tv_lagou_autor)
		TextView tv_lagou_autor;
		public ViewHolder(View view) {
			ButterKnife.bind(this, view);
		}
	}
	@Override
	public int getCount() {
		return companyInfos.size();
	}

	@Override
	public CompanyInfo getItem(int position) {
		return companyInfos.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	// 刷新列表中的数据
	public void refresh(List<CompanyInfo> list) {
		companyInfos = list;
		notifyDataSetChanged();
	}

}
