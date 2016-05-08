package com.tedu.lagou.ui;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.im.util.BmobNetUtil;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.utils.BmobLog;
import com.tedu.lagou.R;
import com.tedu.lagou.bean.CompanyUser;
import com.tedu.lagou.constant.Installation;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
/**
 * @author LWQ
 *企业注册模块
 */
public class HrRegisterActivity extends BaseActivity {
	@Bind(R.id.et_com_username) EditText et_com_username;
	@Bind(R.id.et_com_password) EditText et_com_password;
	@Bind(R.id.et_com_repassword) EditText et_com_repassword;
	@Bind(R.id.btn_hrregister) Button btn_hrregister;
	CompanyUser com_user;
	 //头部标题
//    @Bind(R.id.headerview) View headerView; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hrregister);
		//
		ButterKnife.bind(this);
		
		//初始化头部
//		initHeaderView();
		//初始化
//		initView();
		
		btn_hrregister.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				hrRegister();
			}

		});
	}
/*	private void initHeaderView() {
		 setHeaderTitle(headerView, getResources().getString(R.string.register_txt4));
	}*/
	public void hrRegister() {
			String name=et_com_username.getText().toString().trim();
			String password = et_com_password.getText().toString().trim();
			String pwd_again = et_com_repassword.getText().toString().trim();
			
			//判空
			if (isEmpty(et_com_password,et_com_repassword,et_com_username)) {
				return;
			}
			if (isEmail(name)) {
				et_com_username.setError(Html.fromHtml("<font color=#ff0000>请输入正确的邮箱</font>"));
			}
			if (!pwd_again.equals(password)) {
				toast(getResources().getString(R.string.toast_error_comfirm_password));
				return;
			}
			
			/*if (TextUtils.isEmpty(name)) {
				ShowToast(R.string.toast_error_username_null);
				return;
			}
	
			if (TextUtils.isEmpty(password)) {
				ShowToast(R.string.toast_error_password_null);
				return;
			}
			if (!pwd_again.equals(password)) {
				ShowToast(R.string.toast_error_comfirm_password);
				return;
			}*/
			
			//检查网络状态
			/*boolean isNetConnected = CommonUtils.isNetworkAvailable(HrRegisterActivity.this);
			if(!isNetConnected){
				ShowToast(R.string.network_tips);
				return;
			}*/
	
			if (!BmobNetUtil.isNetworkAvailable(this)) {
				toast(getResources().getString(R.string.network_tip));
				return;
			}
			
			final ProgressDialog progress = new ProgressDialog(HrRegisterActivity.this);
			progress.setMessage("正在注册...");
			progress.setCanceledOnTouchOutside(false);
			progress.show();
			//由于每个应用的注册所需的资料都不一样，故IM sdk未提供注册方法，用户可按照bmod SDK的注册方式进行注册。
			//注册的时候需要注意两点：1、User表中绑定设备id和type，2、设备表中绑定username字段
			com_user = new CompanyUser();
			com_user.setC_email(name);
			com_user.setDeviceType("android");
//			TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); String DEVICE_ID = tm.getDeviceId();
			//设置设备id
			com_user.setInstallId(Installation.id(this));
			String md5 = new String(
					Hex.encodeHex(
					DigestUtils.sha(password))).toUpperCase();
			
			com_user.setC_password(md5);
			com_user.save(this, new SaveListener() {
				
				@Override
				public void onSuccess() {
					progress.dismiss();
//					jump(MainActivity.class, true);
					Intent intent = new Intent(HrRegisterActivity.this,MainActivity.class);
//					intent.putExtra("com_user",com_user);
					
					//用Bundle携带数据
				    Bundle bundle=new Bundle();
				    //传递name参数为tinyphp
//				    bundle.putString("com_user",com_user.getC_email());
				    intent.putExtra("com_user", com_user.getC_email());
//				    intent.putExtras(bundle);
				    startActivity(intent);
					finish();
					toast("注册成功");
					//第一个参数为启动时动画效果，第二个参数为退出时动画效果  
			        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);  
				}
				
				@Override
				public void onFailure(int errcode, String msg) {
					progress.dismiss();
					BmobLog.i(msg);
					toastAndLog("注册失败:" + msg,"错误代码:"+errcode);
				}
			});
	}
	@Override
	public void onBackPressed() {
		 finish();
	}
}
	

