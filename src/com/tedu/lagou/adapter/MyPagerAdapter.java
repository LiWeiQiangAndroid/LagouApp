package com.tedu.lagou.adapter;

import java.util.ArrayList;
import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.tedu.lagou.fragment.FindFragment;
import com.tedu.lagou.fragment.HomeFragment;
import com.tedu.lagou.fragment.MyFragment;
import com.tedu.lagou.fragment.NewsFragment;



public class MyPagerAdapter extends FragmentPagerAdapter{
	
	List<Fragment> fragments = new ArrayList<Fragment>();
	
	public MyPagerAdapter(FragmentManager fm) {
		super(fm);
		fragments.add(new HomeFragment());
		fragments.add(new NewsFragment());
		fragments.add(new FindFragment());
		fragments.add(new MyFragment());
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

}
