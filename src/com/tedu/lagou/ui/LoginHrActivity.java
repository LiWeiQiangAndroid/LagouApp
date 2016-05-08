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
 * ��ҵ��½ģ��
 */
public class LoginHrActivity extends BaseActivity implements
		View.OnClickListener {
	@Bind(R.id.et_hr_email)
	EditText et_hr_email; // ��ҵ����
	@Bind(R.id.et_hr_password)
	EditText et_hr_password; // ��ҵ��½����
	@Bind(R.id.cb_hr_pass)
	CheckBox cb_hr_pass; // ��ʾ����
	@Bind(R.id.btn_hr_login)
	Button btn_hr_login; // ��½
	@Bind(R.id.tv_hr_register)
	TextView tv_hr_register; // ��ҵע��
	@Bind(R.id.cb_hr_remenber)
	CheckBox cb_hr_remenber; // ��ס����
	// ͷ������
/*	@Bind(R.id.headerview)
	View headerView;*/
	private SharedPreferences sp;// ����һ��SharedPreferences
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hrlogin);
		initView();
		/*// ��ʼ��ͷ��
		initHeaderView();*/
		//���ʵ������  
        sp = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);
      //�жϼ�ס�����ѡ���״̬  
        if(sp.getBoolean("ISCHECK", false))  
          {  
            //����Ĭ���Ǽ�¼����״̬  
        	cb_hr_remenber.setChecked(true);  
        	et_hr_email.setText(sp.getString("USER_NAME", ""));  
        	et_hr_password.setText(sp.getString("PASSWORD", ""));  
          }  
        
      //������ס�����ѡ��ť�¼�  
        cb_hr_remenber.setOnCheckedChangeListener(new OnCheckedChangeListener() {  
            public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {  
                if (cb_hr_remenber.isChecked()) {  
                      
                    System.out.println("��ס������ѡ��");  
                    sp.edit().putBoolean("ISCHECK", true).commit();  
                      
                }else {  
                      
                    System.out.println("��ס����û��ѡ��");  
                    sp.edit().putBoolean("ISCHECK", false).commit();  
                      
                }  
  
            }  
        });  
	}
/*
	private void initHeaderView() {
		setHeaderTitle(headerView, "��ҵ��½");

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
			// 3)������û������
			if (!BmobNetUtil.isNetworkAvailable(this)) {
				toast("�ף����粻����Ŷ�����Ժ����ԡ�");
				return;
			}
			login();
		}

	}

	// ��½
	private void login() {
		final String username = et_hr_email.getText().toString().trim();
		final String password = et_hr_password.getText().toString().trim();

		// �п�
		if (isEmpty(et_hr_email, et_hr_password)) {
			return;
		}

		if (!isEmail(username)) {
			toast("�������");
			return;
		}
		final ProgressDialog progress = new ProgressDialog(LoginHrActivity.this);
		progress.setMessage("���ڵ�½...");
		progress.setCanceledOnTouchOutside(false);
		progress.show();

		BmobQuery<CompanyUser> query = new BmobQuery<CompanyUser>();
		query.addWhereEqualTo("c_email", username);// ��ѯcompany��c_email�ֶ���username
		// ���ݷ��������صĽ�������ж��û�������û����Ƿ���ȷ
		query.findObjects(this, new FindListener<CompanyUser>() {

			@Override
			public void onSuccess(List<CompanyUser> object) {
				// ����������˲�ѯ
				if (object.size() > 0) {
					// ������ѯ�ɹ�������ȷʵ�����������û���Ϊname���û�
					// ����û�����ȷ���ٱȶ������������������ϱ���ĸ��û������Ƿ�һ��
					String md5 = new String(Hex.encodeHex(DigestUtils
							.sha(password))).toUpperCase();

					String p = object.get(0).getC_password();

					if (md5.equals(p)) {
						// ����������ȷ
						// ������ת
						/*
						 * Intent intent = new
						 * Intent(LoginHrActivity.this,MainActivity.class);
						 * startActivity(intent); finish();
						 */
						progress.dismiss();
						// jump(MainActivity.class, true);

		                    if(cb_hr_remenber.isChecked())  
		                    {  
		                     //��ס�û��������롢  
		                      Editor editor = sp.edit();  
		                      editor.putString("USER_NAME", username);  
		                      editor.putString("PASSWORD",password);  
		                      editor.commit();  
		                    }  
		                    //��ת����
						Intent intent = new Intent(LoginHrActivity.this,
								MainActivity.class);

						// ��BundleЯ������
						Bundle bundle = new Bundle();
						// ����name����Ϊtinyphp
						bundle.putString("com_user", username);
						intent.putExtras(bundle);
						startActivity(intent);
						toast("��½�ɹ���");
						finish();
					} else {
						progress.dismiss();
						toast("�û������������");
					}
				} else {
					progress.dismiss();
					// ��Ȼ����������˲�ѯ�����ǣ���������ȷʵû���û���Ϊname���û�
					toast("�û������������");
				}
			}

			@Override
			public void onError(int errcode, String msg) {
				progress.dismiss();
				if (errcode == 9016) {
					toast("���������ӣ�������������״̬��");
				}
				toast("�û�������");
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
	 * ��׽�����¼���ť
	 * 
	 * ��Ϊ�� Activity �̳� TabActivity �� onKeyDown ����Ӧ�����Ը��� dispatchKeyEvent һ���
	 * Activity �� onKeyDown �Ϳ�����
	 */
	/**
	 * �˳�����
	 */
	private void exitApp() {
		// �ж�2�ε���¼�ʱ��
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(LoginHrActivity.this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT)
					.show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
		}
	}

}