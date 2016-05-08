package com.tedu.lagou.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import cn.bmob.v3.a.a.This;
import android.util.Log;
import android.widget.Toast;

public class CommonUtil implements ICommon {
	
	public static String formatDateTime(String time) {
		//time 2016-04-20 16:05:07
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm"); 
		if(time==null ||"".equals(time)){
			return "";
		}
		Date date = null;
		try {
			date = format.parse(time);
			Log.d("tedu", "ת����ʱ�䣺"+date); //04-20 18:14:36.397: D/tedu(20527): ʱ��ת����16:36:51

		} catch (ParseException e) {
			e.printStackTrace();
		}
		Calendar current = Calendar.getInstance();
		
		Calendar today = Calendar.getInstance();	//����
		
		today.set(Calendar.YEAR, current.get(Calendar.YEAR));
		today.set(Calendar.MONTH, current.get(Calendar.MONTH));
		today.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH));
		//  Calendar.HOUR����12Сʱ�Ƶ�Сʱ�� Calendar.HOUR_OF_DAY����24Сʱ�Ƶ�Сʱ��
		today.set( Calendar.HOUR_OF_DAY, 0);
		today.set( Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		
		Calendar yesterday = Calendar.getInstance();	//����
		
		yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
		yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
		yesterday.set(Calendar.DAY_OF_MONTH,current.get(Calendar.DAY_OF_MONTH)-1);
		yesterday.set( Calendar.HOUR_OF_DAY, 0);
		yesterday.set( Calendar.MINUTE, 0);
		yesterday.set(Calendar.SECOND, 0);
		
		current.setTime(date);
		
		if(current.after(today)){
			return  time.split(" ")[1];
		}else if(current.before(today) && current.after(yesterday)){
			
//			return time.split(" ")[1];
			return "����";
		}else{
			int index = time.indexOf("-")+1; //2016-04-21 15:21
//			return time.substring(index, time.length());
			String t=time.substring(index, time.length());//t=04-21 15:21
			String MM=t.substring(0, 2); //��ȡ�·�
			String dd=t.substring(3, 5);	//��ȡ����
			return MM+"��"+dd+"��"; //�õ�04��21��
		}
	}
}
