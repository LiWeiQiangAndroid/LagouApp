package com.tedu.lagou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import com.tedu.lagou.R;

public class SuccessActivity extends BaseActivity {
	@Bind(R.id.headerview) View headerView;
	@Bind(R.id.btn_success) Button btn_success;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_success);
		ButterKnife.bind(this);
		initHeaderView();
		initView();
		
	}

	private void initView() {
		 setHeaderImage(headerView, R.drawable.icon_findback_pre, Position.LEFT, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 finish();
			}
		});
		
	}

	private void initHeaderView() {
		 setHeaderTitle(headerView, "¹§Ï²Äã£¡");
	}
	@OnClick(R.id.btn_success)
	public void doClick(){
		finish();
	}
}
