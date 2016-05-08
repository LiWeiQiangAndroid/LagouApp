package com.tedu.lagou.ui;


import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.im.util.BmobNetUtil;
import cn.bmob.v3.BmobInstallation;
import cn.bmob.v3.listener.SaveListener;

import com.tedu.lagou.R;
import com.tedu.lagou.bean.User;
import com.tedu.lagou.constant.Constant;

/**
 * @author LWQ
 *用户注册模块
 */
public class RegisterActivity extends BaseActivity {
	
	//注册手机号
    @Bind(R.id.et_user_phone) EditText etUserPhone;
    //注册邮箱
    @Bind(R.id.et_user_email) EditText etUserEmail;
    //注册密码
    @Bind(R.id.et_password) EditText etPassword;
    //重复密码
    @Bind(R.id.et_repassword) EditText etRepassword;
    //注册提交
    @Bind(R.id.btn_register) Button btnRegister;
    //头部标题
//    @Bind(R.id.headerview) View headerView; 
	
//	BmobChatUser currentUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);

/*		et_username = (EditText) findViewById(R.id.et_username);
		et_password = (EditText) findViewById(R.id.et_password);
		btn_register = (Button) findViewById(R.id.btn_register);*/
		
		ButterKnife.bind(this);
		//初始化头部
//		initHeaderView();
	}
	

/*	private void initHeaderView() {
		 setHeaderTitle(headerView, "欢迎使用");
	}
*/

	@OnClick(R.id.btn_register)
	 public void register(){
		final String name=etUserEmail.getText().toString().trim();
		final String phone=etUserPhone.getText().toString();
	        //1)判空
	        if (isEmpty(etUserPhone,etPassword,etRepassword,etUserEmail)){
	             return;
	        }
	        if (!isMobileNO(phone)){
	            toast("手机号不正确！");
	            return;
	        }

	        //2)两次密码是否一致
	        if (!etPassword.getText().toString().equals(etRepassword.getText().toString())) {
	            toast(getResources().getString(R.string.toast_error_comfirm_password));
	            etPassword.setText("");
	            etRepassword.setText("");
	            return;
	        }
	        //3)看看有没有网络
	        if (!BmobNetUtil.isNetworkAvailable(this)){
	            toast(getResources().getString(R.string.network_tip));
	            return;
	        }
		
		/*final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		progress.setMessage("正在注册...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		//由于每个应用的注册所需的资料都不一样，故IM sdk未提供注册方法，用户可按照bmod SDK的注册方式进行注册。
		//注册的时候需要注意两点：1、User表中绑定设备id和type，2、设备表中绑定username字段
		final User user = new User();
		user.setUsername(name);
		user.setMobilePhoneNumber(phone);
		user.setEmail(name);
		user.setPassword(password);

		//		//将user和设备id进行绑定
//		user.setDeviceType("android");
		
		user.signUp(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				progress.dismiss();
				ShowToast("注册成功");
				//发广播通知登陆页面退出
				sendBroadcast(new Intent(Constants.ACTION_REGISTER_SUCCESS_FINISH));
				// 启动主页
				Intent intent = new Intent(RegisterActivity.this,MainActivity.class);
				intent.putExtra("user",user);
				startActivity(intent);
				finish();
//				overridePendingTransition(R.anim.fade_in, R.anim.fade_out);  
//				overridePendingTransition(android.R.anim.slide_in_left,android.R.anim.slide_out_right);  
			}
			
			@Override
			public void onFailure(int errcode, String msg) {
				// TODO Auto-generated method stub
				switch (errcode) {
				case 301:
					ShowToast(phone+"不是正确的手机号");
					break;
				case 209:
					ShowToast(phone+"该手机号已被使用！");
					
				}
				BmobLog.i(msg);
				ShowToast("注册失败:" + msg+"错误代码:"+errcode);
				progress.dismiss();
			}
		});
	}

}
*/      
		        final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		        progress.setMessage("正在注册...");
		        progress.setCanceledOnTouchOutside(false);
		        progress.show();

		        //进行联网注册
		        final User user=new User();
		        /*String md5=new String(
		                Hex.encodeHex(DigestUtils.sha(
		                        etPassword.getText().toString() ))).toUpperCase();*/

		        user.setUsername(name);
		        user.setEmail(name);
		        user.setPassword(etPassword.getText().toString());
		        user.setMobilePhoneNumber(etUserPhone.getText().toString());
		        //设定当前用户使用的设备类型，只能输入“android”或“ios”
		        user.setDeviceType("android");
		        //把当前设备的ID与用户进行一个绑定
		        user.setInstallId(BmobInstallation.getInstallationId(this));
		        //真正进行注册
		        user.signUp(this, new SaveListener() {
		            @Override
		            public void onSuccess() {
		                progress.dismiss();
		                //1)要在设备表(_Installation)中，将当前登录用户的用户名与设备ID再进行一次绑定
		                //bindInstallationForRegister(user.getUsername())该方法执行也就意味着完成了“登录”操作
		                bmobUserManager.bindInstallationForRegister(user.getUsername());
		                
		                toast("注册成功");
		                //2)更新用户的位置(用MyApp.lastPoint设置用户的point属性的值)
		                
		                updateUserLocation(user);
		              //发广播通知登陆页面退出
		                sendBroadcast(new Intent(Constant.ACTION_REGISTER_SUCCESS_FINISH));
		/*                // 启动主页
		                Intent intent = new Intent(RegistActivity.this,MainActivity.class);
		                intent.putExtra("user",user);
		                startActivity(intent);
		                finish();*/

		                //3界面跳转MainActivity
		                jump(MainActivity.class,true);
		                
		            }

		            @Override
		            public void onFailure(int i, String s) {
		                toastAndLog("注册失败，请稍后重试！",i+"： "+s);
		                progress.dismiss();
		                switch (i) {
						case 202:
							toast(etUserEmail.getText().toString()+"已被注册！");
							break;
						}
		            }
		        });
		    }
}