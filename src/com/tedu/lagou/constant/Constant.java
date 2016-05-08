package com.tedu.lagou.constant;

import android.annotation.SuppressLint;
import android.os.Environment;


/** 
  * @ClassName: BmobConstants
  * 作者：LiWeiQiang on 2016/4/11 09:16
  * 邮箱：lwqldsyzx@126.com
  */
@SuppressLint("SdCardPath")
public class Constant {

	public static String Bmob_Key = "5b9befc961ba4b6470c859e444eec38e";
	
	//百度地图定位
	private static final int REQUEST_TIMEOUT = 10*1000;//设置请求超时10秒钟  
	private static final int SO_TIMEOUT = 10*1000;  //设置等待数据超时时间10秒钟 
	/**
	 * 存放发送图片的目录
	 */
	public static String BMOB_PICTURE_PATH = Environment.getExternalStorageDirectory()	+ "/bmobimdemo/image/";
	
	/**
	 * 我的头像保存目录
	 */
	public static String MyAvatarDir = "/sdcard/bmobimdemo/avatar/";
	/**
	 * 拍照回调
	 */
	public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;//拍照修改头像
	public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;//本地相册修改头像
	public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;//系统裁剪头像
	
	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//拍照
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//本地图片
	public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;//位置
	public static final String EXTRA_STRING = "extra_string";
	
	public static enum Position{LEFT,CENTER,RIGHT}
	public static final String ACTION_REGISTER_SUCCESS_FINISH ="register.success.finish";//注册成功后退出登陆业进入主页
}
