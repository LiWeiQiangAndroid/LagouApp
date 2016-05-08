package com.tedu.lagou.ui;

import java.util.List;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.bmob.im.util.BmobNetUtil;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;

import com.tedu.lagou.R;
import com.tedu.lagou.bean.CompanyUser;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.CompoundButton.OnCheckedChangeListener;

/*
 * @author LWQ
 * 企业登陆模块
 */
public class LoginHrActivity extends BaseActivity implements
		View.OnClickListener {
	@Bind(R.id.et_hr_email)
	EditText et_hr_email; // 企业邮箱
	@Bind(R.id.et_hr_password)
	EditText et_hr_password; // 企业登陆密码
	@Bind(R.id.cb_hr_pass)
	CheckBox cb_hr_pass; // 显示密码
	@Bind(R.id.btn_hr_login)
	Button btn_hr_login; // 登陆
	@Bind(R.id.tv_hr_register)
	TextView tv_hr_register; // 企业注册
	@Bind(R.id.cb_hr_remenber)
	CheckBox cb_hr_remenber; // 记住密码
	// 头部标题
/*	@Bind(R.id.headerview)
	View headerView;*/
	private SharedPreferences sp;// 声明一个SharedPreferences
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hrlogin);
		initView();
		/*// 初始化头部
		initHeaderView();*/
		//获得实例对象  
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
      //判断记住密码多选框的状态  
        if(sp.getBoolean("ISCHECK", false))  
          {  
            //设置默认是记录密码状态  
        	cb_hr_remenber.setChecked(true);  
        	et_hr_email.setText(sp.getString("USER_NAME", ""));  
        	et_hr_password.setText(sp.getString("PASSWORD", ""));  
          }  
        
      //监听记住密码多选框按钮事件  
        cb_hr_remenber.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (cb_hr_remenber.isChecked()) {  
                      
                    System.out.println("记住密码已选中");  
                    sp.edit().putBoolean("ISCHECK", true).commit();  
                      
                }else {  
                      
                    System.out.println("记住密码没有选中");  
                    sp.edit().putBoolean("ISCHECK", false).commit();  
                      
                }  
  
            }  
        });  
	}
/*
	private void initHeaderView() {
		setHeaderTitle(headerView, "企业登陆");

	}
*/
	private void initView() {
		ButterKnife.bind(this);
		et_hr_email.setOnClickListener(this);
		tv_hr_register.setOnClickListener(this);
		btn_hr_login.setOnClickListener(this);
		this.cb_hr_pass.setOnCheckedChangeListener(new OnClickListenerImpl());
		
	}

	@Override
	public void onClick(View v) {
		if (v == tv_hr_register) {
			/*
			 * Intent intent = new Intent(LoginHrActivity.this,
			 * HrRegisterActivity.class); startActivity(intent);
			 */
			jump(HrRegisterActivity.class, false);
			overridePendingTransition(android.R.anim.slide_out_right,android.R.anim.slide_in_left);

		} else {
			// 3)看看有没有网络
			if (!BmobNetUtil.isNetworkAvailable(this)) {
				toast("亲，网络不给力哦，请稍后再试。");
				return;
			}
			login();
		}

	}

	// 登陆
	private void login() {
		final String username = et_hr_email.getText().toString().trim();
		final String password = et_hr_password.getText().toString().trim();

		// 判空
		if (isEmpty(et_hr_email, et_hr_password)) {
			return;
		}

		if (!isEmail(username)) {
			toast("邮箱错误！");
			return;
		}
		final ProgressDialog progress = new ProgressDialog(LoginHrActivity.this);
		progress.setMessage("正在登陆...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();

		BmobQuery<CompanyUser> query = new BmobQuery<CompanyUser>();
		query.addWhereEqualTo("c_email", username);// 查询company表c_email字段中username
		// 根据服务器返回的结果，先判断用户输入的用户名是否正确
		query.findObjects(this, new FindListener<CompanyUser>() {

			@Override
			public void onSuccess(List<CompanyUser> object) {
				// 服务器完成了查询
				if (object.size() > 0) {
					// 不但查询成功，而且确实服务器上有用户名为name的用户
					// 如果用户名正确，再比对输入的密码与服务器上保存的该用户密码是否一致
					String md5 = new String(Hex.encodeHex(DigestUtils
							.sha(password))).toUpperCase();

					String p = object.get(0).getC_password();

					if (md5.equals(p)) {
						// 密码输入正确
						// 界面跳转
						/*
						 * Intent intent = new
						 * Intent(LoginHrActivity.this,MainActivity.class);
						 * startActivity(intent); finish();
						 */
						progress.dismiss();
						// jump(MainActivity.class, true);

		                    if(cb_hr_remenber.isChecked())  
		                    {  
		                     //记住用户名、密码、  
		                      Editor editor = sp.edit();  
		                      editor.putString("USER_NAME", username);  
		                      editor.putString("PASSWORD",password);  
		                      editor.commit();  
		                    }  
		                    //跳转界面
						Intent intent = new Intent(LoginHrActivity.this,
								MainActivity.class);

						// 用Bundle携带数据
						Bundle bundle = new Bundle();
						// 传递name参数为tinyphp
						bundle.putString("com_user", username);
						intent.putExtras(bundle);
						startActivity(intent);
						toast("登陆成功！");
						finish();
					} else {
						progress.dismiss();
						toast("用户名或密码错误");
					}
				} else {
					progress.dismiss();
					// 虽然服务器完成了查询，但是，服务器上确实没有用户名为name的用户
					toast("用户名或密码错误");
				}
			}

			@Override
			public void onError(int errcode, String msg) {
				progress.dismiss();
				if (errcode == 9016) {
					toast("无网络连接，请检查您的网络状态。");
				}
				toast("用户不存在");
				log(errcode + ":" + msg);
			}
		});

	}

	private class OnClickListenerImpl implements OnCheckedChangeListener {

		@Override
		public void onCheckedChanged(CompoundButton buttonView,
				boolean isChecked) {
			if (cb_hr_pass.isChecked()) {
				et_hr_password
						.setTransformationMethod(HideReturnsTransformationMethod
								.getInstance());
			} else {
				et_hr_password
						.setTransformationMethod(PasswordTransformationMethod
								.getInstance());
			}

		}
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
		if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
			if (event.getAction() == KeyEvent.ACTION_DOWN
					&& event.getRepeatCount() == 0) {
				this.exitApp();
			}
			return true;
		}
		return super.dispatchKeyEvent(event);
	}

	private long exitTime = 0;

	/**
	 * 捕捉返回事件按钮
	 * 
	 * 因为此 Activity 继承 TabActivity 用 onKeyDown 无响应，所以改用 dispatchKeyEvent 一般的
	 * Activity 用 onKeyDown 就可以了
	 */
	/**
	 * 退出程序
	 */
	private void exitApp() {
		// 判断2次点击事件时间
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(LoginHrActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT)
					.show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
		}
	}

}