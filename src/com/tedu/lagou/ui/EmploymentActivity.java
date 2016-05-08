package com.tedu.lagou.ui;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.tedu.lagou.R;
import com.tedu.lagou.adapter.EmploymentAdapter;
import com.tedu.lagou.bean.CompanyInfo;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
/**
 * 
 * @author LWQ
 *招聘详情
 */
public class EmploymentActivity extends BaseActivity {
	String companyUser; // 企业号
	String job;// 职位

	List<CompanyInfo> mcompanyInfos = new ArrayList<CompanyInfo>();; // 数据源
	private EmploymentAdapter madapter;

	@Bind(R.id.lv_list)
	ListView lv_list;
	@Bind(R.id.headerView)
	View headerView;
	// 发送简历
	@Bind(R.id.tv_lagou_send)
	TextView lagou_send;
	// 聊聊
	@Bind(R.id.btn_lagou_talk)
	Button lagou_talk;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_employment);
		ButterKnife.bind(this);
		companyUser = getIntent().getStringExtra("companyUser");
		job = getIntent().getStringExtra("job");
		log("EmploymentActivity->companyUser:" + companyUser);
		log("EmploymentActivity->job:" + job);
		initHeaderView();
		// 初始化数据源
		// initdata();
		initListView();
	}

	@Override
	protected void onResume() {
		initdata();
		super.onResume();
	}

	private void initListView() {
		madapter = new EmploymentAdapter(this, mcompanyInfos);
		log("EmploymentActivity->mcompanyInfos 2:" + mcompanyInfos);
		lv_list.setAdapter(madapter);

	}

	public void initdata() {
		BmobQuery<CompanyInfo> query = new BmobQuery<CompanyInfo>();
		query.addWhereEqualTo("c_email", companyUser);
		query.addWhereEqualTo("job", job);
		log("EmploymentActivity->initdata->companyUser:" + companyUser);
		log("EmploymentActivity->initdata->job:" + job);
		CompanyInfo companyInfo = new CompanyInfo();
		query.findObjects(this, new FindListener<CompanyInfo>() {
			@Override
			public void onSuccess(List<CompanyInfo> arg0) {
				log("查询成功：共" + arg0.size() + "条数据。");
				mcompanyInfos.clear();
				for (CompanyInfo company : arg0) {
					CompanyInfo companyInfo = new CompanyInfo();
					companyInfo.setC_email(company.getC_email());
					companyInfo.setUrl(company.getUrl());
					companyInfo.setJob(company.getJob());
					companyInfo.setCompanyName(company.getCompanyName());
					companyInfo.setSalary(company.getSalary());
					companyInfo.setAddress(company.getAddress());
					companyInfo.setWorkTime(company.getWorkTime());
					companyInfo.setEducation(company.getEducation());
					companyInfo.setFinance(company.getFinance());
					companyInfo.setPeople(company.getPeople());
					companyInfo.setIndustry(company.getIndustry());
					companyInfo.setContent(company.getContent());
					log("查询到" + "图片地址：" + company.getUrl() + "职位:"
							+ company.getJob() + "发布时间:" + company.getDate()
							+ "薪资:" + company.getSalary() + "地址:"
							+ company.getAddress() + "工作经验:"
							+ company.getWorkTime() + "公司名:"
							+ company.getCompanyName() + "要求学历:"
							+ company.getEducation() + "公司类型:"
							+ company.getFinance() + "公司人数:"
							+ company.getPeople() + "产业类型:"
							+ company.getIndustry() + "描述:"
							+ company.getContent());
					mcompanyInfos.add(companyInfo);
					log("EmploymentActivity->mcompanyInfos:" + mcompanyInfos);
				}
				madapter.notifyDataSetChanged();

			}

			@Override
			public void onError(int arg0, String arg1) {

			}
		});
	}

	private void initHeaderView() {
		setHeaderTitle(headerView, "职位详情");
		setHeaderImage(headerView, R.drawable.icon_findback_pre, Position.LEFT,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});

	}

	@Override
	public void onBackPressed() {
		log("返回销毁EmploymentActivity");
		finish();
	}

}
