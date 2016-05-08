package com.tedu.lagou.fragment;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.tedu.lagou.R;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class NewsFragment extends BaseFragment {
	@Bind(R.id.headerview)
	View headerView;
	// ˭������
	@Bind(R.id.rl_seeme)
	View seeme;
	// Ͷ��֪ͨ
	@Bind(R.id.rl_message)
	View message;
	// ְλ����֪ͨ
	@Bind(R.id.rl_callFor)
	View callFor;
	// ��ϵ�ͷ�
	@Bind(R.id.rl_service)
	View service;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.frag_news, null);
		ButterKnife.bind(this, view);
		initHeaderView();
		return view;
	}

	private void initHeaderView() {
		setHeaderTitle(headerView, "��Ϣ�б�");
	}

	@OnClick({ R.id.rl_seeme, R.id.rl_message, R.id.rl_callFor, R.id.rl_service })
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_seeme:
			toast("��������...");
			break;
		case R.id.rl_message:
			toast("��������...");
			break;
		case R.id.rl_callFor:
			toast("��������...");
			break;
		case R.id.rl_service:
			toast("��������...");
			break;
		}
	}
}
