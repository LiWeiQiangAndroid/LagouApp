package com.tedu.lagou.ui;

import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.widget.RadioGroup;
import butterknife.Bind;
import butterknife.ButterKnife;

import com.tedu.lagou.R;
import com.tedu.lagou.adapter.MyPagerAdapter;

public class MainActivity extends BaseActivity implements OnPageChangeListener {

	@Bind(R.id.rg_tab_menu) RadioGroup rgTabMenu;
	@Bind(R.id.vp_container) ViewPager vpContainer;
//	private FragmentPagerAdapter adapter;
	MyPagerAdapter adapter;
	
	
	long firstPress;// 记录第一次按下“back”键的时间戳

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		ButterKnife.bind(this);

//		vpContainer = (ViewPager) findViewById(R.id.vp_container);
//		rgTabMenu = (RadioGroup) findViewById(R.id.rg_tab_menu);

		InnerOnCheckedChangeListener listener = new InnerOnCheckedChangeListener();
		rgTabMenu.setOnCheckedChangeListener(listener);

//		adapter = new InnerPagerAdapter(getSupportFragmentManager());
		adapter = new MyPagerAdapter(getSupportFragmentManager());
		vpContainer.setAdapter(adapter);
		vpContainer.setOnPageChangeListener(this);
	}

	/*private class InnerPagerAdapter extends FragmentPagerAdapter {

		public InnerPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {
			Fragment frag = null;
			switch (position) {
			case 0:
				frag = new HomeFragment();
				break;
			case 1:
				frag = new NewsFragment();
				break;
			case 2:
				frag = new FindFragment();
				break;
			case 3:
				frag = new MyFragment();
				break;
			}
			return frag;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}

	}*/

	private class InnerOnCheckedChangeListener implements
			RadioGroup.OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			int itemIndex = 0;
			switch (checkedId) {
			case R.id.rb_tab_menu_home:
				// tvContent.setText("团购");
				itemIndex = 0;
				break;

			case R.id.rb_tab_menu_news:
				// tvContent.setText("附近");
				itemIndex = 1;
				break;

			case R.id.rb_tab_menu_find:
				// tvContent.setText("我的");
				itemIndex = 2;
				break;

			case R.id.rb_tab_menu_my:
				// tvContent.setText("更多");
				itemIndex = 3;
				break;
			}
			vpContainer.setCurrentItem(itemIndex);
		}

	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {
	}

	@Override
	public void onPageSelected(int position) {
		int checkId = R.id.rb_tab_menu_home;
		switch (position) {
		case 1:
			checkId = R.id.rb_tab_menu_news;
			break;

		case 2:
			checkId = R.id.rb_tab_menu_find;
			break;

		case 3:
			checkId = R.id.rb_tab_menu_my;
			break;
		}
		rgTabMenu.check(checkId);
	}

	/**
	 * 两次按back键退出
	 */

	@Override
	public void onBackPressed() {

		if (firstPress + 2000 > System.currentTimeMillis()) {
			super.onBackPressed();
		} else {
			firstPress = System.currentTimeMillis();
			toast("再按一次退出");
		}

	}

}
