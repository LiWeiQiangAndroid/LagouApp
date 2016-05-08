package com.tedu.lagou.ui;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.im.util.BmobLog;
import cn.bmob.im.util.BmobNetUtil;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.SaveListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.tedu.lagou.R;
import com.tedu.lagou.bean.CompanyInfo;
/**
 * 
 * @author LWQ 
 *������Ƹ
 */
public class ReleaseActivity extends BaseActivity {

	@Bind(R.id.headerview)
	View headerView;

	@Bind(R.id.iv_regist_avatar) ImageView ivAvatar; // logo
	
	@Bind(R.id.et_job)
	EditText et_job; // ��Ƹְλ����
	@Bind(R.id.et_companyname)
	EditText companyName; // ��˾��
	@Bind(R.id.et_address)
	EditText address; // ��ַ
	@Bind(R.id.et_workTime)
	EditText workTime;// ��������
	@Bind(R.id.et_education)
	EditText education;// ѧ��
	@Bind(R.id.et_finance)
	EditText finance; // ����
	@Bind(R.id.et_people)
	EditText people;// ��˾����
	@Bind(R.id.et_industry)
	EditText industry;// ��ҵ����
	@Bind(R.id.et_salary)
	EditText salary;// н��
	@Bind(R.id.add_content)
	EditText AddContent;// ְλ����
	//������ť
	@Bind(R.id.btn_submit)
	Button btn_submit;
	
	private String filePath;//�û�ͷ��ͼ��ı��ص�ַ
	private String photoPath;//����ͷ����Ƭ�ı��ص�ַ
	private String avatarUrl;//�û�ͷ��ͼ���ϴ��ɹ����ڷ������ϵĵ�ַ
//	CompanyUser companyUser;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
		setContentView(R.layout.activity_release);
		ButterKnife.bind(this);
		initHeaderView();
	}
 
	private void initHeaderView() {
		setHeaderTitle(headerView, "������Ƹ");
		setHeaderImage(headerView, R.drawable.icon_findback_pre, Position.LEFT, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 finish();
			}
		});
	}

	@OnClick(R.id.btn_submit)
	public void doClick(View v) {
 		// �ռ��û���������ݣ�����һ�����󣬱��浽������
		Intent intent=getIntent();
		String name=intent.getStringExtra("com_user");
		Log.d("tedu", "�����ǻ�ȡ���û�����"+name);
		if (isEmpty(et_job, companyName, address, workTime, education, finance,
				people, industry, salary, AddContent)) {
			return;
		}

		// ����
		if (!BmobNetUtil.isNetworkAvailable(this)) {
			toast("�ף����粻����Ŷ�����Ժ����ԡ�");
			return;
		}
		
		/*
		BmobQuery<CompanyUser> query=new BmobQuery<CompanyUser>();
		
		query.addWhereEqualTo("c_email", name);// ��ѯcompany��c_email�ֶ���username
		BmobLog.d("tedu", this.getClass()+":"+name);
		query.findObjects(this, new FindListener<CompanyUser>() {
			
			@Override
			public void onSuccess(List<CompanyUser> object) {
				// ����������˲�ѯ
				if (object.size() > 0) {
					companyUser=object.get(0);
					Log.d("tedu", "�û�����"+companyUser.getC_email());
				}
			}
			@Override
			public void onError(int arg0, String arg1) {
				 BmobLog.d("tedu", this.getClass()+":"+arg0+":"+arg1);
				
			}
		});*/
		
		final CompanyInfo userinfo = new CompanyInfo();
		// ָ���û���ͷ��ĵ�ַ
		if (TextUtils.isEmpty(avatarUrl)) {
			userinfo.setUrl("");
		} else {
			userinfo.setUrl(avatarUrl);
		}
		BmobLog.d("tedu", this.getClass()+":"+avatarUrl);
//		userinfo.setCompanyUser(companyUser);
//		Log.d("tedu", "CompanyUser:"+companyUser);
		userinfo.setDate(new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date()));
		userinfo.setC_email(name);
		userinfo.setJob(et_job.getText().toString());
		userinfo.setCompanyName(companyName.getText().toString());
		userinfo.setAddress(address.getText().toString());
		userinfo.setWorkTime(workTime.getText().toString());
		userinfo.setEducation(education.getText().toString());
		userinfo.setFinance(finance.getText().toString());
		userinfo.setPeople(people.getText().toString());
		userinfo.setIndustry(industry.getText().toString());
		userinfo.setSalary(salary.getText().toString());
		userinfo.setContent(AddContent.getText().toString());
		
		userinfo.save(this, new SaveListener() {

			@Override
			public void onSuccess() {
				// ����ɹ�����һЩ������
				avatarUrl = "";
				et_job.setText("");
				companyName.setText("");
				address.setText("");
				workTime.setText("");
				education.setText("");
				finance.setText("");
				people.setText("");
				industry.setText("");
				salary.setText("");
				ivAvatar.setImageDrawable(null);
				AddContent.setText("");
				jump(SuccessActivity.class, true);
				toast("�����ɹ���");
			}

			@Override
			public void onFailure(int errcode, String msg) {
				toast("����ʧ��");
				toastAndLog("������Ϣ��", errcode + ":" + msg);
				return;
			}
		});

	}

	@OnClick(R.id.iv_regist_avatar)
	public void setAvatar(View v){
		//����Intent
		//������ϵͳͼ���Intent
		Intent intent1 = new Intent(Intent.ACTION_PICK);
		intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

		//������ϵͳ�����Intnet
		Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//ָ��������Ƭ�����λ��
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),System.currentTimeMillis()+".png");
		photoPath = file.getAbsolutePath();
		Uri imgUri = Uri.fromFile(file);
		intent2.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);

		//����Intentѡ����
		Intent chooser = Intent.createChooser(intent1, "��ѡ��ͷ��...");
		chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});

		//����intent
		startActivityForResult(chooser, 101);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK && requestCode==101){
			if(data!=null){
				//�û��Ǵ�ͼ��ѡ��ͼ
				//�û�ѡ��������ͼ
				//��uri������ͼƬ��SD���ϵľ���·��
				//���Ǹ�ͼƬ��ͼ���е�����(content://xxxx/9)
				Uri uri = data.getData();
				//����ContentResolverȥͼ���в�ѯ��uri��ӦͼƬ��SD�ϵľ���·��
				Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
				c.moveToNext();
				filePath = c.getString(0);
				c.close();

			}else{
				//�û��Ǵ��������
				filePath = photoPath;
			}

			//�ϴ�ͼ�ε�Bmob�ļ�������
			BmobProFile.getInstance(this).upload(filePath, new UploadListener() {

				@Override
				public void onError(int arg0, String arg1) {
					Toast.makeText(ReleaseActivity.this, "ͷ���ϴ�ʧ�ܣ����Ժ�����", Toast.LENGTH_SHORT).show();
					Log.d("TAG", "�������:"+arg0+","+arg1);
				}

				@Override
				public void onSuccess(String arg0, String arg1, BmobFile arg2) {
					Toast.makeText(ReleaseActivity.this, "ͷ���ϴ��ɹ�", Toast.LENGTH_SHORT).show();
					//���ͷ���ϴ����������󣬷������ϵı����ַ
					avatarUrl = arg2.getUrl();
					//��֤ͷ���ϴ��ɹ����ٽ��û�ѡ��/���յ�ͷ��ͼƬ������ivAvatar����ʾ
					ivAvatar.setImageBitmap(BitmapFactory.decodeFile(filePath));
				}

				@Override
				public void onProgress(int arg0) {
					// TODO Auto-generated method stub

				}
			});

		}
	}

}

