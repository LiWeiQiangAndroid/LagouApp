package com.tedu.lagou.util;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.tedu.lagou.R;

import android.text.TextUtils;
import android.widget.ImageView;

public class ImageUtil {
	public static void displayImage(String url,ImageView iv){
		if(TextUtils.isEmpty(url)){
			iv.setImageResource(R.drawable.ic_launcher);
		}else{
			ImageLoader.getInstance().displayImage(url, iv);
		}
	}
}
