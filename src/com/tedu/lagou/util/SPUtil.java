package com.tedu.lagou.util;

import com.tedu.lagou.app.MyApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author Administrator
 *	用来设置编好设置文件的工具类
 */
public class SPUtil {
	private static Editor editor;
	private SharedPreferences sp;
	
	public SPUtil(String spName) {
		sp=MyApp.context.getSharedPreferences(spName,Context.MODE_PRIVATE);
		editor=sp.edit();
	}
	/**
	 * 是否接受通知
	 */
	public boolean isAcceptNtify(){
		return sp.getBoolean("notify", true);
	}
	
	/**
	 * 是否允许声音
	 */
	public void setAllowSound(boolean flag){
		editor.putBoolean("sound", flag);
		editor.commit();
	}
	/**
	 * 设置是否允许震动
	 * @param flag
	 */
	public void setAllowVibrate(boolean flag){
		editor.putBoolean("vibrate", flag);
		editor.commit();
	}
}
