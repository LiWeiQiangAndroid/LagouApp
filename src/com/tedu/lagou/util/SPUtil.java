package com.tedu.lagou.util;

import com.tedu.lagou.app.MyApp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author Administrator
 *	�������ñ�������ļ��Ĺ�����
 */
public class SPUtil {
	private static Editor editor;
	private SharedPreferences sp;
	
	public SPUtil(String spName) {
		sp=MyApp.context.getSharedPreferences(spName,Context.MODE_PRIVATE);
		editor=sp.edit();
	}
	/**
	 * �Ƿ����֪ͨ
	 */
	public boolean isAcceptNtify(){
		return sp.getBoolean("notify", true);
	}
	
	/**
	 * �Ƿ���������
	 */
	public void setAllowSound(boolean flag){
		editor.putBoolean("sound", flag);
		editor.commit();
	}
	/**
	 * �����Ƿ�������
	 * @param flag
	 */
	public void setAllowVibrate(boolean flag){
		editor.putBoolean("vibrate", flag);
		editor.commit();
	}
}
