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
 *�û�ע��ģ��
 */
public class RegisterActivity extends BaseActivity {
	
	//ע���ֻ���
    @Bind(R.id.et_user_phone) EditText etUserPhone;
    //ע������
    @Bind(R.id.et_user_email) EditText etUserEmail;
    //ע������
    @Bind(R.id.et_password) EditText etPassword;
    //�ظ�����
    @Bind(R.id.et_repassword) EditText etRepassword;
    //ע���ύ
    @Bind(R.id.btn_register) Button btnRegister;
    //ͷ������
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
		//��ʼ��ͷ��
//		initHeaderView();
	}
	

/*	private void initHeaderView() {
		 setHeaderTitle(headerView, "��ӭʹ��");
	}
*/

	@OnClick(R.id.btn_register)
	 public void register(){
		final String name=etUserEmail.getText().toString().trim();
		final String phone=etUserPhone.getText().toString();
	        //1)�п�
	        if (isEmpty(etUserPhone,etPassword,etRepassword,etUserEmail)){
	             return;
	        }
	        if (!isMobileNO(phone)){
	            toast("�ֻ��Ų���ȷ��");
	            return;
	        }

	        //2)���������Ƿ�һ��
	        if (!etPassword.getText().toString().equals(etRepassword.getText().toString())) {
	            toast(getResources().getString(R.string.toast_error_comfirm_password));
	            etPassword.setText("");
	            etRepassword.setText("");
	            return;
	        }
	        //3)������û������
	        if (!BmobNetUtil.isNetworkAvailable(this)){
	            toast(getResources().getString(R.string.network_tip));
	            return;
	        }
		
		/*final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		progress.setMessage("����ע��...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();
		//����ÿ��Ӧ�õ�ע����������϶���һ������IM sdkδ�ṩע�᷽�����û��ɰ���bmod SDK��ע�᷽ʽ����ע�ᡣ
		//ע���ʱ����Ҫע�����㣺1��User���а��豸id��type��2���豸���а�username�ֶ�
		final User user = new User();
		user.setUsername(name);
		user.setMobilePhoneNumber(phone);
		user.setEmail(name);
		user.setPassword(password);

		//		//��user���豸id���а�
//		user.setDeviceType("android");
		
		user.signUp(this, new SaveListener() {
			
			@Override
			public void onSuccess() {
				progress.dismiss();
				ShowToast("ע��ɹ�");
				//���㲥֪ͨ��½ҳ���˳�
				sendBroadcast(new Intent(Constants.ACTION_REGISTER_SUCCESS_FINISH));
				// ������ҳ
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
					ShowToast(phone+"������ȷ���ֻ���");
					break;
				case 209:
					ShowToast(phone+"���ֻ����ѱ�ʹ�ã�");
					
				}
				BmobLog.i(msg);
				ShowToast("ע��ʧ��:" + msg+"�������:"+errcode);
				progress.dismiss();
			}
		});
	}

}
*/      
		        final ProgressDialog progress = new ProgressDialog(RegisterActivity.this);
		        progress.setMessage("����ע��...");
		        progress.setCanceledOnTouchOutside(false);
		        progress.show();

		        //��������ע��
		        final User user=new User();
		        /*String md5=new String(
		                Hex.encodeHex(DigestUtils.sha(
		                        etPassword.getText().toString() ))).toUpperCase();*/

		        user.setUsername(name);
		        user.setEmail(name);
		        user.setPassword(etPassword.getText().toString());
		        user.setMobilePhoneNumber(etUserPhone.getText().toString());
		        //�趨��ǰ�û�ʹ�õ��豸���ͣ�ֻ�����롰android����ios��
		        user.setDeviceType("android");
		        //�ѵ�ǰ�豸��ID���û�����һ����
		        user.setInstallId(BmobInstallation.getInstallationId(this));
		        //��������ע��
		        user.signUp(this, new SaveListener() {
		            @Override
		            public void onSuccess() {
		                progress.dismiss();
		                //1)Ҫ���豸��(_Installation)�У�����ǰ��¼�û����û������豸ID�ٽ���һ�ΰ�
		                //bindInstallationForRegister(user.getUsername())�÷���ִ��Ҳ����ζ������ˡ���¼������
		                bmobUserManager.bindInstallationForRegister(user.getUsername());
		                
		                toast("ע��ɹ�");
		                //2)�����û���λ��(��MyApp.lastPoint�����û���point���Ե�ֵ)
		                
		                updateUserLocation(user);
		              //���㲥֪ͨ��½ҳ���˳�
		                sendBroadcast(new Intent(Constant.ACTION_REGISTER_SUCCESS_FINISH));
		/*                // ������ҳ
		                Intent intent = new Intent(RegistActivity.this,MainActivity.class);
		                intent.putExtra("user",user);
		                startActivity(intent);
		                finish();*/

		                //3������תMainActivity
		                jump(MainActivity.class,true);
		                
		            }

		            @Override
		            public void onFailure(int i, String s) {
		                toastAndLog("ע��ʧ�ܣ����Ժ����ԣ�",i+"�� "+s);
		                progress.dismiss();
		                switch (i) {
						case 202:
							toast(etUserEmail.getText().toString()+"�ѱ�ע�ᣡ");
							break;
						}
		            }
		        });
		    }
}