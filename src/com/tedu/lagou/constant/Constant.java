package com.tedu.lagou.constant;

import android.annotation.SuppressLint;
import android.os.Environment;


/** 
  * @ClassName: BmobConstants
  * ���ߣ�LiWeiQiang on 2016/4/11 09:16
  * ���䣺lwqldsyzx@126.com
  */
@SuppressLint("SdCardPath")
public class Constant {

	public static String Bmob_Key = "5b9befc961ba4b6470c859e444eec38e";
	
	//�ٶȵ�ͼ��λ
	private static final int REQUEST_TIMEOUT = 10*1000;//��������ʱ10����  
	private static final int SO_TIMEOUT = 10*1000;  //���õȴ����ݳ�ʱʱ��10���� 
	/**
	 * ��ŷ���ͼƬ��Ŀ¼
	 */
	public static String BMOB_PICTURE_PATH = Environment.getExternalStorageDirectory()	+ "/bmobimdemo/image/";
	
	/**
	 * �ҵ�ͷ�񱣴�Ŀ¼
	 */
	public static String MyAvatarDir = "/sdcard/bmobimdemo/avatar/";
	/**
	 * ���ջص�
	 */
	public static final int REQUESTCODE_UPLOADAVATAR_CAMERA = 1;//�����޸�ͷ��
	public static final int REQUESTCODE_UPLOADAVATAR_LOCATION = 2;//��������޸�ͷ��
	public static final int REQUESTCODE_UPLOADAVATAR_CROP = 3;//ϵͳ�ü�ͷ��
	
	public static final int REQUESTCODE_TAKE_CAMERA = 0x000001;//����
	public static final int REQUESTCODE_TAKE_LOCAL = 0x000002;//����ͼƬ
	public static final int REQUESTCODE_TAKE_LOCATION = 0x000003;//λ��
	public static final String EXTRA_STRING = "extra_string";
	
	public static enum Position{LEFT,CENTER,RIGHT}
	public static final String ACTION_REGISTER_SUCCESS_FINISH ="register.success.finish";//ע��ɹ����˳���½ҵ������ҳ
}
