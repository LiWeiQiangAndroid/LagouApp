package com.tedu.lagou.ui;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.update.BmobUpdateAgent;

import com.tedu.lagou.R;
import com.tedu.lagou.util.DataCleanManager;

/**
 * 更多设置
 * 
 */
public class MoreSettingsActivity extends BaseActivity implements View.OnClickListener {
	Context mContext;
	// 标题
	@Bind(R.id.headerview)
	View headerView;

	@Bind(R.id.tv_setting_login)
	TextView setting_login;
	// 切换企业版
	@Bind(R.id.tv_setting_qy)
	TextView setting_qy;
	// 消息设置
	@Bind(R.id.tv_setting_news)
	TextView setting_news;
	// 清除缓存
	@Bind(R.id.tv_setting_cache)
	TextView setting_cache;
	// 关于
	@Bind(R.id.tv_setting_about)
	TextView setting_about;
	// 协议
	@Bind(R.id.tv_setting_xieyi)
	TextView setting_xieyi;
	//检查更新
	@Bind(R.id.tv_setting_gengxin)
	TextView setting_gengxin;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_settings);
		ButterKnife.bind(this);
		initHeaderView();
		initView();
	}

	private void initView() {
		setting_gengxin.setOnClickListener(this);
		if (getIntent().getStringExtra("com_user") == null) {
			setting_qy.setVisibility(View.INVISIBLE);
		}
		if (bmobUserManager.getCurrentUser() != null) {
			setting_login.setText("退出登陆");
			setting_login.setOnClickListener(logoutimpl);
		} else {
			setting_login.setOnClickListener(loginimpl);
		}
	}

	private void initHeaderView() {
		// 设置背景色
		// headerView.setBackgroundColor(Color.rgb(247,247,249));
		// 设置标题
		setHeaderTitle(headerView, "设置");
		// 设置头部标题文字颜色
		setHeaderTitleColor(headerView, Color.BLACK);
		setHeaderImage(headerView, R.drawable.icon_findback_pre, Position.LEFT,
				new OnClickListener() {

					@Override
					public void onClick(View v) {
						finish();
					}
				});
	}

	Logoutimpl logoutimpl = new Logoutimpl();
	Loginimpl loginimpl = new Loginimpl();

	private class Logoutimpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			bmobUserManager.logout();
			jump(LoginActivity.class, true);
		}
	}

	private class Loginimpl implements OnClickListener {

		@Override
		public void onClick(View v) {
			jump(LoginActivity.class, true);
		}
	}

	@OnClick({ R.id.tv_setting_qy, R.id.tv_setting_news, R.id.tv_setting_cache,
			R.id.tv_setting_about, R.id.tv_setting_xieyi })
	public void OnClick(View v) {
		switch (v.getId()) {
		case R.id.tv_setting_qy:
			jump(LoginHrActivity.class, true);
			break;

		case R.id.tv_setting_news:
			showDialog();
			break;
		case R.id.tv_setting_cache:
			DataCleanManager.cleanInternalCache(this);
			toast("清除成功！");
			break;
		case R.id.tv_setting_about:
			showDialog();
			break;
		case R.id.tv_setting_xieyi:
			showDialog();
			break;
		}
	}
 

	@Override
	public void onBackPressed() {
		finish();
	}

	@Override
	public void onClick(View v) {
		Toast.makeText(this, "你点击了更新！", Toast.LENGTH_SHORT).show();
		BmobUpdateAgent.forceUpdate(this);
	}
}
