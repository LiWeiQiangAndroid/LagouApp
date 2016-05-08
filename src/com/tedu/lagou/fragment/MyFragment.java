package com.tedu.lagou.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.im.util.BmobLog;

import com.tedu.lagou.R;
import com.tedu.lagou.ui.LoginActivity;
import com.tedu.lagou.ui.MoreSettingsActivity;
import com.tedu.lagou.ui.MyReleaseActivity;
import com.tedu.lagou.ui.ReleaseActivity;
import com.tedu.lagou.ui.ResumeActivity;
import com.tedu.lagou.ui.UserInfoActivity;

public class MyFragment extends BaseFragment {
	@Bind(R.id.tv_my_login)
	Button my_login; // 登陆或注册
	@Bind(R.id.tv_lagou_gdsz)
	TextView lagou_gdsz; // 更多设置
	@Bind(R.id.iv_icon)
	View ivicon; // 头像
	@Bind(R.id.tv_user)
	TextView tvUser; // 用户名

	@Bind(R.id.tv_lagou_jl)
	TextView tv_lagou_jl; // 简历
	
	@Bind(R.id.tv_lagou_plus)
	TextView tv_lagou_plus; // Plus
	
	@Bind(R.id.tv_lagou_sc)
	View tv_lagou_sc; // 收藏
	
	@Bind(R.id.tv_lagou_yjfk)
	View tv_lagou_yjfk; // 意见反馈
	
	@Bind(R.id.r_fbjl)
	View r_fbjl; // 发布招聘
	
	@Bind(R.id.r_wfbd)
	View r_wfbd; // 我发布的

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_my, container, false);
		ButterKnife.bind(this, view);
		initView();
		return view;
	}

	private void initView() {

		if (getActivity().getIntent().getStringExtra("com_user") != null) {
			r_fbjl.setVisibility(View.VISIBLE);
			r_wfbd.setVisibility(View.VISIBLE);
			ivicon.setVisibility(View.VISIBLE);
			my_login.setVisibility(View.INVISIBLE);
			tvUser.setVisibility(View.VISIBLE);
			r_fbjl.setBackgroundResource(R.color.base_color_text_white);
			r_wfbd.setBackgroundResource(R.color.base_color_text_white);
			log("用户名：" + getActivity().getIntent().getStringExtra("com_user"));
			tvUser.setText("用户名:"
					+ getActivity().getIntent().getStringExtra("com_user"));
			// 如果没有登陆用户则显示登陆/注册,有登陆用户则隐藏

		} else if (bmobUserManager.getCurrentUser() != null) {
			my_login.setVisibility(View.INVISIBLE);
			ivicon.setVisibility(View.VISIBLE);
			tvUser.setVisibility(View.VISIBLE);
			tvUser.setText("用户名:" + bmobUserManager.getCurrentUserName());
			ivicon.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					jump(UserInfoActivity.class, false);
				}
			});
		} else {
			my_login.setVisibility(View.VISIBLE);
			ivicon.setVisibility(View.INVISIBLE);
		}

	}

	@OnClick({R.id.tv_my_login,R.id.tv_lagou_jl,
		R.id.tv_lagou_plus,R.id.tv_lagou_sc,R.id.tv_lagou_yjfk,R.id.tv_lagou_gdsz,
		R.id.r_wfbd,R.id.r_fbjl})
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.tv_lagou_gdsz:
			jump(MoreSettingsActivity.class, false);
			break;
		case R.id.tv_my_login:
			jump(LoginActivity.class, false);
			break;
		case R.id.r_fbjl:
			// jump(ReleaseActivity.class, false);
			Intent intent = new Intent(getActivity(), ReleaseActivity.class);
			/*
			 * intent.putExtra("com_user",
			 * getActivity().getIntent().getStringExtra("com_user"));
			 * Log.d("tedu",
			 * "R.id.r_fbjl:"+getActivity().getIntent().getStringExtra
			 * ("com_user"));
			 */
			intent.putExtra("com_user", getActivity().getIntent()
					.getStringExtra("com_user"));
			// 直接添加
			// intent.putExtra("MyData", new ParcelableData());
			startActivity(intent);
			BmobLog.d("tedu", "getActivity().getIntent().getStringExtra"
					+ getActivity().getIntent().getStringExtra("com_user"));
			break;
		case R.id.r_wfbd:
			String name = getActivity().getIntent().getStringExtra("com_user");
			Intent intent2 = new Intent(getActivity(), MyReleaseActivity.class);
			intent2.putExtra("name", name);
			startActivity(intent2);
			// jump(MyReleaseActivity.class, false);
			break;
		case R.id.tv_lagou_jl:
			jump(ResumeActivity.class, false); // 跳转到我的简历
			break;
		case R.id.tv_lagou_plus:
			alert();
			break;
		case R.id.tv_lagou_sc:
			alert();
			break;
		case R.id.tv_lagou_yjfk:
			alert();
			break;
		}
	}

	public void alert() {
		new AlertDialog.Builder(getActivity())
				.setTitle(
						this.getResources().getString(
								R.string.dialog_message_title))
				.setMessage(
						this.getResources().getString(R.string.error_aialog))
				.setNegativeButton(android.R.string.ok,
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog,
									int which) {
								dialog.dismiss();
							}
						}).show();
	}

	private class alert extends DialogFragment {

	}
}
