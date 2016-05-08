package com.tedu.lagou.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.SectionIndexer;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.tedu.lagou.R;
import com.tedu.lagou.bean.CompanyInfo;
import com.tedu.lagou.util.CommonUtil;
import com.tedu.lagou.util.ImageLoader;

public class CompanyInfoAdapter extends MyBaseAdapter<CompanyInfo> implements
		SectionIndexer {
	Context context;
	List<CompanyInfo> companyInfos;
	LayoutInflater inflater;
	ImageLoader imageLoader;

	public CompanyInfoAdapter(Context context, List<CompanyInfo> companyInfos) {
		super(context, companyInfos);
		this.context = context;
		this.companyInfos = companyInfos;
		this.inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// this.inflater=LayoutInflater.from(context);
		imageLoader = new ImageLoader(context.getApplicationContext());
	}

	@Override
	public View getItemView(int position, View convertView, ViewGroup parent) {
		ViewHolder vh;
		if (convertView == null) {
			convertView = inflater.inflate(R.layout.lagou_item_view, parent,
					false);
			vh = new ViewHolder(convertView);

			convertView.setTag(vh);
		} else {
			vh = (ViewHolder) convertView.getTag();
		}

		CompanyInfo companyInfo = getItem(position);
		// logo
		if (TextUtils.isEmpty(companyInfo.getUrl())) {
			vh.iv_lagou_logo.setImageResource(R.drawable.ic_launcher);
		} else {
			// ImageLoader.getInstance().displayImage(companyInfo.getUrl(),
			// vh.iv_lagou_logo);
			// ImageUtil.displayImage(companyInfo.getUrl(), vh.iv_lagou_logo);
			imageLoader.DisplayImage(companyInfo.getUrl(), vh.iv_lagou_logo);
		}
		/*
		 * if (TextUtils.isEmpty(companyInfo.getUrl())) {
		 * vh.iv_lagou_logo.setImageResource(R.drawable.ic_launcher);;
		 * 
		 * }else { Drawable
		 * cacheImage=asyncImageLoader.loadDrawable(companyInfo.getUrl() , new
		 * ImageCallback() {
		 * 
		 * @Override public void ImageLoaded(Drawable imageDrawable, String
		 * imageUrl) { vh.iv_lagou_logo.setImageDrawable(imageDrawable);
		 * 
		 * } }); vh.iv_lagou_logo.setImageDrawable(cacheImage); }
		 */
		
		vh.tv_lagou_companyUser.setText(companyInfo.getC_email());
		// 设置招聘职位
		vh.tv_lagou_job.setText(companyInfo.getJob());
		// 2016-04-19 16:36:51 companyInfo.getAddTime()
		vh.tv_lagou_addtime.setText(CommonUtil.formatDateTime(companyInfo
				.getDate()));
		vh.tv_lagou_companyname.setText(companyInfo.getCompanyName());
		vh.tv_lagou_salary.setText(companyInfo.getSalary());
		vh.tv_lagou_address.setText(companyInfo.getAddress());
		vh.tv_lagou_workTime.setText(companyInfo.getWorkTime());
		vh.tv_lagou_education.setText(companyInfo.getEducation());
		vh.tv_lagou_finance.setText(companyInfo.getFinance());
		vh.tv_lagou_people.setText(companyInfo.getPeople());
		vh.tv_lagou_industry.setText(companyInfo.getIndustry());

		return convertView;
	}

	public class ViewHolder {
		@Bind(R.id.iv_lagou_logo)
		ImageView iv_lagou_logo;
		@Bind(R.id.tv_lagou_job)
		TextView tv_lagou_job;
		@Bind(R.id.tv_lagou_addtime)
		TextView tv_lagou_addtime;
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
		@Bind(R.id.tv_lagou_companyUser)
		TextView tv_lagou_companyUser;

		public ViewHolder(View convertView) {
			ButterKnife.bind(this, convertView);
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

	@Override
	public Object[] getSections() {
		// 没用
		return null;
	}

	@Override
	public int getPositionForSection(int section) {
		for (int i = 0; i < getCount(); i++) {
			if (section == getSectionForPosition(i)) {
				return i;
			}
		}
		return -1;
	}

	@Override
	public int getSectionForPosition(int position) {

		return 0;
	}

}
