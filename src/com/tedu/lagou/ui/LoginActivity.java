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
 * �û���½ģ��
 */
public class LoginActivity extends BaseActivity implements OnClickListener,OnCheckedChangeListener {

/*	@Bind(R.id.headerview)
	View headerView;	//������HeaderView
*/	@Bind(R.id.et_username)
	EditText et_username; // �û���
	@Bind(R.id.et_password)
	EditText et_password; // ����
	@Bind(R.id.btn_login)
	Button btn_login; // ��½
	@Bind(R.id.tv_register)
	TextView tv_register; // ע��
	@Bind(R.id.tv_qy_login)
	TextView tv_qy_login; // ��ҵ��½
	@Bind(R.id.cb_pass)
	CheckBox cb_pass; // ��ʾ����
	@Bind(R.id.tv_wangjimima)
	TextView tv_wangjimima;// ��������
	@Bind(R.id.tv_lagou_main)
	TextView tv_lagou_main; //������ҳ

	private MyBroadcastReceiver receiver = new MyBroadcastReceiver();
	
	long firstPress;//��¼��һ�ΰ��¡�back������ʱ���
	
	@SuppressWarnings("rawtypes")
	// BmobPushManager bmobPushManager;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		BmobUpdateAgent.setUpdateOnlyWifi(false);
		 BmobUpdateAgent.update(this);
		setContentView(R.layout.activity_login);
		//�󶨻�ţ��
		ButterKnife.bind(this);
		//��ʼ��ͷ��
//		initHeaderView();
		//������
		init();
		
		//ע���˳��㲥
		IntentFilter filter = new IntentFilter();
		filter.addAction(Constant.ACTION_REGISTER_SUCCESS_FINISH);
		registerReceiver(receiver, filter);
	}

	/*private void initHeaderView() {
		 setHeaderTitle(headerView, "��ӭʹ��");
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
			// 3)������û������
			if (!BmobNetUtil.isNetworkAvailable(this)) {
				toast("�ף����粻����Ŷ�����Ժ����ԡ�");
				return;
			}
			login();
		}
	}

	/*
	 * �û���½
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

		// 1)�п�
		if (isEmpty(et_username,et_password)) {
			return;
		}
		
		//2��֤����
		if (!isEmail(username)) {
			toast("�����ʽ����,��˶ԣ�");
			return;
		}

		final ProgressDialog progress = new ProgressDialog(LoginActivity.this);
		progress.setMessage("���ڵ�½...");
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
					/*// ��ȡ����ǰ�û�����Ϣ
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
					// ��¼�ɹ�
//					progress.dismiss();
					toast("��¼�ɹ�");
					// overridePendingTransition(R.anim.fade_in,
					// R.anim.fade_out);
				} else {
					toast("����δ��֤���������½��");
					progress.dismiss();
					return;
				}
			}

			@Override
			public void onFailure(int errorcode, String msg) {
				if (errorcode == 109) {
					toast("����δ��֤���뵽��������֤");
					progress.dismiss();
					return;
				}
				if (errorcode == 101) {
					toast("�û��������벻��ȷ");
					progress.dismiss();
					return;
				}
				progress.dismiss();
				BmobLog.i(msg);
				// ShowToast(msg);
				// ShowToast("������"+errorcode);
				BmobLog.i(errorcode + "");
			}
		});
	}

	/*
	 * ��������
	 */
	private void wjmm(){
		// ��ȡ����
		final String wjmm = et_username.getText().toString().trim();
		
		// �ж������Ƿ�Ϊ��
		if (TextUtils.isEmpty(wjmm)) {
			toast("����������Ҫ���˻��������������Ա��һ����롣");
			return;
		}
		if (!isEmail(wjmm)) {
			// ����������������������������ȷ������
			toast("����������������������������ȷ�����䡣");
			return;
		}
		
		// �ж��Ƿ���ȷ������
		// if (isEmail(wjmm)) {
		BmobUser.resetPasswordByEmail(LoginActivity.this, wjmm,
				new ResetPasswordByEmailListener() {
					@Override
					public void onSuccess() {
						// ShowToast("����:"+wjmm+" "+getResources().getString(R.string.forget_password_send_email));
						// �����ʼ��ɹ�,�뵽�����������롣
						toast("����:"+ wjmm+"�����ʼ��ɹ�,�뵽�����������롣");
					}
		
					@Override
					public void onFailure(int errcode, String msg) {
						// �ʼ�����ʧ�ܣ���ȷ�������ʼ���ַ
						toastAndLog("�ʼ�����ʧ��!","�������"+errcode+"��Ϣ"+msg);
					}
				});
		}
	
	/**
	 * �����
	 */
	
	@OnClick(R.id.tv_lagou_main)
	public void main(){
		jump(MainActivity.class, true);
	}
	/**
	 * �㲥
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
	 * ���ΰ�back���˳�
	 */

	@Override
	public void onBackPressed() {

		if(firstPress+2000>System.currentTimeMillis()){
			super.onBackPressed();
		}else{
			firstPress = System.currentTimeMillis();
			toast("����ٰ�һ����");
		}
	}
	
}
