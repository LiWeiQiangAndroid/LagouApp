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
 *��Ƹ����
 */
public class EmploymentActivity extends BaseActivity {
	String companyUser; // ��ҵ��
	String job;// ְλ

	List<CompanyInfo> mcompanyInfos = new ArrayList<CompanyInfo>();; // ����Դ
	private EmploymentAdapter madapter;

	@Bind(R.id.lv_list)
	ListView lv_list;
	@Bind(R.id.headerView)
	View headerView;
	// ���ͼ���
	@Bind(R.id.tv_lagou_send)
	TextView lagou_send;
	// ����
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
		// ��ʼ������Դ
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
				log("��ѯ�ɹ�����" + arg0.size() + "�����ݡ�");
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
					log("��ѯ��" + "ͼƬ��ַ��" + company.getUrl() + "ְλ:"
							+ company.getJob() + "����ʱ��:" + company.getDate()
							+ "н��:" + company.getSalary() + "��ַ:"
							+ company.getAddress() + "��������:"
							+ company.getWorkTime() + "��˾��:"
							+ company.getCompanyName() + "Ҫ��ѧ��:"
							+ company.getEducation() + "��˾����:"
							+ company.getFinance() + "��˾����:"
							+ company.getPeople() + "��ҵ����:"
							+ company.getIndustry() + "����:"
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
		setHeaderTitle(headerView, "ְλ����");
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
		log("��������EmploymentActivity");
		finish();
	}

}
