package com.tedu.lagou.ui;

import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.v3.datatype.BmobGeoPoint;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.tedu.lagou.R;
import com.tedu.lagou.app.MyApp;
import com.tedu.lagou.bean.User;


public class SplashActivity extends BaseActivity {
	
/*	TextView tv_la;
	
	TextView tv_gou;*/
	
   @Bind(R.id.tv_splash_la)
    TextView tvSplashLa; //��������
    @Bind(R.id.tv_splash_gou)
    TextView tvSplashGou; //"������
    @Bind(R.id.tv_splash_wang)
    TextView tvsplashwang;//"��"��
    
    @Bind(R.id.layout_welcome)
    View welcome;
	
	LocationClient client;//�ٶȶ�λ�ͻ���
	MyBDLocationListener listener;//��λ�ص�������

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//��SplashActivity����ռ��ȫ��Ļ
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_splash);
		ButterKnife.bind(this);
/*		tv_la=(TextView) findViewById(R.id.tv_splash_la);
		tv_gou=(TextView) findViewById(R.id.tv_splash_gou);*/
		
		initBaiduLocation();// ��ʼ���ٶȵ�ͼ��λ�ͻ��ˣ�������λ
	}
	
	/**
	 * ��ʼ���ٶȵ�ͼ��λ�ͻ��ˣ�������λ
	 */
	private void initBaiduLocation() {
		//�����ٶȵ�ͼ��λ�ͻ��˶���
		client = new LocationClient(this);
		//���ðٶȵ�ͼ��λ�������Ӱٶȿ�������վcopy�Ĵ��룩
		LocationClientOption option = new LocationClientOption();
		option.setLocationMode(LocationMode.Hight_Accuracy
				);//��ѡ��Ĭ�ϸ߾��ȣ����ö�λģʽ���߾��ȣ��͹��ģ����豸
		option.setCoorType("bd09ll");//��ѡ��Ĭ��gcj02�����÷��صĶ�λ�������ϵ
		int span=1000;
		option.setScanSpan(span);//��ѡ��Ĭ��0��������λһ�Σ����÷���λ����ļ����Ҫ���ڵ���1000ms������Ч��
		option.setIsNeedAddress(true);//��ѡ�������Ƿ���Ҫ��ַ��Ϣ��Ĭ�ϲ���Ҫ
		option.setOpenGps(true);//��ѡ��Ĭ��false,�����Ƿ�ʹ��gps
		option.setLocationNotify(true);//��ѡ��Ĭ��false�������Ƿ�gps��Чʱ����1S1��Ƶ�����GPS���
		option.setIsNeedLocationDescribe(true);//��ѡ��Ĭ��false�������Ƿ���Ҫλ�����廯�����������BDLocation.getLocationDescribe��õ�����������ڡ��ڱ����찲�Ÿ�����
		option.setIsNeedLocationPoiList(true);//��ѡ��Ĭ��false�������Ƿ���ҪPOI�����������BDLocation.getPoiList��õ�
		option.setIgnoreKillProcess(true);//��ѡ��Ĭ��true����λSDK�ڲ���һ��SERVICE�����ŵ��˶������̣������Ƿ���stop��ʱ��ɱ��������̣�Ĭ�ϲ�ɱ��  
		option.SetIgnoreCacheException(false);//��ѡ��Ĭ��false�������Ƿ��ռ�CRASH��Ϣ��Ĭ���ռ�
		option.setEnableSimulateGps(false);//��ѡ��Ĭ��false�������Ƿ���Ҫ����gps��������Ĭ����Ҫ
		//Ϊ��λ�ͻ������ò���
		client.setLocOption(option);
		//������λ��ɺ�ļ�����
		listener = new MyBDLocationListener();
		//Ϊ�ͻ���ע�������
		client.registerLocationListener(listener);
		//����λ����
		client.start();//--->�ٶȵ�ͼ�Ķ�λService(Bind��ʽ����com.baidu.location.f����ʱ��ᱨServiceLeak�쳣)
	}

	public class MyBDLocationListener implements BDLocationListener{
		//��λ��ɺ�ػص��÷�����������λ����Բ�������ʽ���뵽�ص�������
		@Override
		public void onReceiveLocation(BDLocation result) {
			
			int code = result.getLocType();
			
			double lat = 0;
			double lng = 0;
			//ֻ��codeΪ61��66��161��ʱ�򣬲ű�ʾ�������ʵ��λ����Ϣ
			if(code==61||code==66||code==161){
				lat = result.getLatitude();
				lng = result.getLongitude();
			}else{
				//���������ȷ�����Ϣ����ָ��һ��λ�ã���������������ģ������
				lat = 39.876457;
				lng = 116.464899;
			}
			//���û�õľ�γ��ΪMyApp.lastPoint��ֵ
			MyApp.lastPoint = new BmobGeoPoint(lng, lat);
			//��λ�ɹ��󣬿�ʼ����
			startAnimation();
			//��λ�ɹ���ֹͣ������λ
			if(client.isStarted()){
				client.stop();
				client.unRegisterLocationListener(listener);
			}
		}

	}
	
	public void startAnimation() {
		Animation anim = AnimationUtils.loadAnimation(this, R.anim.splash_anim);
		Animation alpha = AnimationUtils.loadAnimation(this, R.anim.alpha);
		welcome.setVisibility(View.VISIBLE);
		tvSplashLa.setVisibility(View.VISIBLE);
		tvSplashGou.setVisibility(View.VISIBLE);
		tvsplashwang.setVisibility(View.VISIBLE);
		welcome.startAnimation(alpha);
		tvSplashLa.startAnimation(anim);
		tvSplashGou.startAnimation(anim);
		tvsplashwang.setAnimation(anim);
		
		//new Handler().postDelay(Runnable,3000);
		//Ϊ���Զ������һ������������
		anim.setAnimationListener(new AnimationListener() {

			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationRepeat(Animation animation) {
			}
			
			//������������ʱ���ص��÷���
			@Override
			public void onAnimationEnd(Animation animation) {
				//�����ǰ�е�¼���û����������û�����̨�豸�ϵ�¼������һֱû�н��еǳ�������
				//��ô����û���һֱ�����ŵ�¼��״̬��
				//��SplashActivity��ת��MainActivity
				//��֮��˵����ǰ��û�е�¼�û��ģ�Ӧ����ת����¼����LoginActivity
				if(bmobUserManager.getCurrentUser()!=null){
					
					updateUserLocation(bmobUserManager.getCurrentUser(User.class));
					
//					Intent intent = new Intent(SplashActivity.this,MainActivity.class);
//					startActivity(intent);
//					finish();
					jump(MainActivity.class, true);
				}else{
//					Intent intent = new Intent(SplashActivity.this,LoginActivity.class);
//					startActivity(intent);
//					finish();
					jump(LoginActivity.class,true);
				}
			}

		});
	}
}
