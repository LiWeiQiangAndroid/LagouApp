package com.tedu.lagou.ui;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.tedu.lagou.R;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;

public class ResumeActivity extends BaseActivity {
	@Bind(R.id.headerview)
	View headerView;
	@Bind(R.id.r_jl) View r_jl;
	@Bind(R.id.r_fjjl) View r_fjjl;
	@Bind(R.id.r_xxmrlj) View r_xxmrlj;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_resume);
		ButterKnife.bind(this);
		initHeaderView();
		
	}
	private void initHeaderView() {
		//设置标题
		setHeaderTitle(headerView, "简历");
		//设置头部标题文字颜色
		setHeaderTitleColor(headerView, Color.BLACK);
		setHeaderImage(headerView, R.drawable.icon_findback_pre, Position.LEFT, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				finish();
			}
		});
	}
	@OnClick({R.id.r_jl,R.id.r_fjjl,R.id.r_xxmrlj})
	public void Onclick(View v){
		switch (v.getId()) {
		case R.id.r_jl:
			showDialog();
			break;
		case R.id.r_fjjl:
			showDialog();
			break;
		case R.id.r_xxmrlj:
			showDialog();
			break;
		}
	}
	@Override
	public void onBackPressed() {
		 finish();
	}
	
}
