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
 *��ҵע��ģ��
 */
public class HrRegisterActivity extends BaseActivity {
	@Bind(R.id.et_com_username) EditText et_com_username;
	@Bind(R.id.et_com_password) EditText et_com_password;
	@Bind(R.id.et_com_repassword) EditText et_com_repassword;
	@Bind(R.id.btn_hrregister) Button btn_hrregister;
	CompanyUser com_user;
	 //ͷ������
//    @Bind(R.id.headerview) View headerView; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hrregister);
		//
		ButterKnife.bind(this);
		
		//��ʼ��ͷ��
//		initHeaderView();
		//��ʼ��
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
			
			//�п�
			if (isEmpty(et_com_password,et_com_repassword,et_com_username)) {
				return;
			}
			if (isEmail(name)) {
				et_com_username.setError(Html.fromHtml("<font color=#ff0000>��������ȷ������</font>"));
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
			
			//�������״̬
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
			progress.setMessage("����ע��...");
			progress.setCanceledOnTouchOutside(false);
			progress.show();
			//����ÿ��Ӧ�õ�ע����������϶���һ������IM sdkδ�ṩע�᷽�����û��ɰ���bmod SDK��ע�᷽ʽ����ע�ᡣ
			//ע���ʱ����Ҫע�����㣺1��User���а��豸id��type��2���豸���а�username�ֶ�
			com_user = new CompanyUser();
			com_user.setC_email(name);
			com_user.setDeviceType("android");
//			TelephonyManager tm = (TelephonyManager)getSystemService(Context.TELEPHONY_SERVICE); String DEVICE_ID = tm.getDeviceId();
			//�����豸id
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
					
					//��BundleЯ������
				    Bundle bundle=new Bundle();
				    //����name����Ϊtinyphp
//				    bundle.putString("com_user",com_user.getC_email());
				    intent.putExtra("com_user", com_user.getC_email());
//				    intent.putExtras(bundle);
				    startActivity(intent);
					finish();
					toast("ע��ɹ�");
					//��һ������Ϊ����ʱ����Ч�����ڶ�������Ϊ�˳�ʱ����Ч��  
			        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);  
				}
				
				@Override
				public void onFailure(int errcode, String msg) {
					progress.dismiss();
					BmobLog.i(msg);
					toastAndLog("ע��ʧ��:" + msg,"�������:"+errcode);
				}
			});
	}
	@Override
	public void onBackPressed() {
		 finish();
	}
}
	

