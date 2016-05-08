package com.tedu.lagou.ui;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.im.util.BmobNetUtil;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.listener.ResetPasswordByEmailListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.update.BmobUpdateAgent;

import com.bmob.utils.BmobLog;
import com.tedu.lagou.R;
import com.tedu.lagou.bean.User;
import com.tedu.lagou.constant.Constant;

/*
 * 用户登陆模块
 */
public class LoginActivity extends BaseActivity implements OnClickListener,OnCheckedChangeListener {

/*	@Bind(R.id.headerview)
	View headerView;	//顶部的HeaderView
*/	@Bind(R.id.et_username)
	EditText et_username; // 用户名
	@Bind(R.id.et_password)
	EditText et_password; // 密码
	@Bind(R.id.btn_login)
	Button btn_login; // 登陆
	@Bind(R.id.tv_register)
	TextView tv_register; // 注册
	@Bind(R.id.tv_qy_login)
	TextView tv_qy_login; // 企业登陆
	@Bind(R.id.cb_pass)
	CheckBox cb_pass; // 显示密码
	@Bind(R.id.tv_wangjimima)
	TextView tv_wangjimima;// 忘记密码
	@Bind(R.id.tv_lagou_main)
	TextView tv_lagou_main; //进入主页

	private MyBroadcastReceiver receiver = new MyBroadcastReceiver();
	
	long firstPress;//记录第一次按下“back”键的时间戳
	
	@SuppressWarnings("rawtypes")
	// BmobPushManager bmobPushManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BmobUpdateAgent.setUpdateOnlyWifi(false);
		 BmobUpdateAgent.update(this);
		setContentView(R.layout.activity_login);
		//绑定黄牛刀
		ButterKnife.bind(this);
		//初始化头部
//		initHeaderView();
		//、监听
		init();
		
		//注册退出广播
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_REGISTER_SUCCESS_FINISH);
		registerReceiver(receiver, filter);
	}

	/*private void initHeaderView() {
		 setHeaderTitle(headerView, "欢迎使用");
	}*/

	private void init() {
		btn_login.setOnClickListener(this);
		tv_register.setOnClickListener(this);
		tv_qy_login.setOnClickListener(this);
		tv_wangjimima.setOnClickListener(this);
		cb_pass.setOnCheckedChangeListener(this);
	}

	@Override
	public void onClick(View v) {
		if (v == tv_register) {
			/*
			 * Intent intent = new Intent(LoginActivity.this,
			 * RegisterActivity.class); startActivity(intent);
			 */
			jump(RegisterActivity.class,false);
			
		} else if (v == tv_qy_login) {
			/*
			 * Intent intent = new Intent(LoginActivity.this,
			 * LoginHrActivity.class); startActivity(intent); finish();
			 */
			jump(LoginHrActivity.class, true);

			overridePendingTransition(android.R.anim.slide_in_left,
					android.R.anim.slide_out_right);
		}else if(v==tv_wangjimima) {
			wjmm();
			
		} else {
			/*
			 * boolean isNetConnected = CommonUtils.isNetworkAvailable(this);
			 * if(!isNetConnected){ ShowToast(R.string.network_tips); return;
			 */
			// 3)看看有没有网络
			if (!BmobNetUtil.isNetworkAvailable(this)) {
				toast("亲，网络不给力哦，请稍后再试。");
				return;
			}
			login();
		}
	}

	/*
	 * 用户登陆
	 */
	private void login() {
		final String username = et_username.getText().toString().trim();
		final String password = et_password.getText().toString().trim();

		/*
		 * if (TextUtils.isEmpty(username)) {
		 * ShowToast(R.string.toast_error_username_null); return; }
		 * 
		 * if (TextUtils.isEmpty(password)) {
		 * ShowToast(R.string.toast_error_password_null); return; }
		 */

		// 1)判空
		if (isEmpty(et_username,et_password)) {
			return;
		}
		
		//2验证邮箱
		if (!isEmail(username)) {
			toast("邮箱格式错误！,请核对！");
			return;
		}

		final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
		progress.setMessage("正在登陆...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		final User user = new User();
		/*String md5=new String(
                Hex.encodeHex(DigestUtils.sha(
                        password))).toUpperCase();*/
		
		user.setUsername(username);
		user.setPassword(password);
		user.login(LoginActivity.this, new SaveListener() {

			@Override
			public void onSuccess() {
				if (user.getEmailVerified()) {
					/*// 获取到当前用户的信息
					User user = BmobUser.getCurrentUser(LoginActivity.this,
							User.class);
					Intent intent = new Intent(LoginActivity.this,
							MainActivity.class);
					
					intent.putExtra("user", user);
					startActivity(intent);
					finish();*/
					jump(MainActivity.class, true);
					overridePendingTransition(android.R.anim.slide_in_left,
							android.R.anim.slide_out_right);
					// 登录成功
//					progress.dismiss();
					toast("登录成功");
					// overridePendingTransition(R.anim.fade_in,
					// R.anim.fade_out);
				} else {
					toast("邮箱未验证！不允许登陆。");
					progress.dismiss();
					return;
				}
			}

			@Override
			public void onFailure(int errorcode, String msg) {
				if (errorcode == 109) {
					toast("邮箱未验证！请到邮箱中验证");
					progress.dismiss();
					return;
				}
				if (errorcode == 101) {
					toast("用户名或密码不正确");
					progress.dismiss();
					return;
				}
				progress.dismiss();
				BmobLog.i(msg);
				// ShowToast(msg);
				// ShowToast("错误码"+errorcode);
				BmobLog.i(errorcode + "");
			}
		});
	}

	/*
	 * 忘记密码
	 */
	private void wjmm(){
		// 获取邮箱
		final String wjmm = et_username.getText().toString().trim();
		
		// 判断输入是否为空
		if (TextUtils.isEmpty(wjmm)) {
			toast("忘记密码需要在账户框中输入邮箱以便找回密码。");
			return;
		}
		if (!isEmail(wjmm)) {
			// 邮箱输入错误，请在邮箱框中输入正确的邮箱
			toast("邮箱输入错误，请在邮箱框中输入正确的邮箱。");
			return;
		}
		
		// 判断是否正确的邮箱
		// if (isEmail(wjmm)) {
		BmobUser.resetPasswordByEmail(LoginActivity.this, wjmm,
				new ResetPasswordByEmailListener() {
					@Override
					public void onSuccess() {
						// ShowToast("已向:"+wjmm+" "+getResources().getString(R.string.forget_password_send_email));
						// 发送邮件成功,请到邮箱重置密码。
						toast("已向:"+ wjmm+"发送邮件成功,请到邮箱重置密码。");
					}
		
					@Override
					public void onFailure(int errcode, String msg) {
						// 邮件发送失败，请确认您的邮件地址
						toastAndLog("邮件发送失败!","错误代码"+errcode+"信息"+msg);
					}
				});
		}
	
	/**
	 * 随便逛逛
	 */
	
	@OnClick(R.id.tv_lagou_main)
	public void main(){
		jump(MainActivity.class, true);
	}
	/**
	 * 广播
	 * @author Administrator
	 *
	 */
	public class MyBroadcastReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			if (intent != null && Constant.ACTION_REGISTER_SUCCESS_FINISH.equals(intent.getAction())) {
				finish();
			}
		}
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		unregisterReceiver(receiver);
	}

	
	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		if (cb_pass.isChecked()) {
			et_password
					.setTransformationMethod(HideReturnsTransformationMethod
							.getInstance());
		} else {
			et_password
					.setTransformationMethod(PasswordTransformationMethod
							.getInstance());
		}
	}
	/**
	 * 两次按back键退出
	 */

	@Override
	public void onBackPressed() {

		if(firstPress+2000>System.currentTimeMillis()){
			super.onBackPressed();
		}else{
			firstPress = System.currentTimeMillis();
			toast("你敢再按一次吗？");
		}
	}
	
}
