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
 *发布招聘
 */
public class ReleaseActivity extends BaseActivity {

	@Bind(R.id.headerview)
	View headerView;

	@Bind(R.id.iv_regist_avatar) ImageView ivAvatar; // logo
	
	@Bind(R.id.et_job)
	EditText et_job; // 招聘职位名称
	@Bind(R.id.et_companyname)
	EditText companyName; // 公司名
	@Bind(R.id.et_address)
	EditText address; // 地址
	@Bind(R.id.et_workTime)
	EditText workTime;// 工作经验
	@Bind(R.id.et_education)
	EditText education;// 学历
	@Bind(R.id.et_finance)
	EditText finance; // 融资
	@Bind(R.id.et_people)
	EditText people;// 公司人数
	@Bind(R.id.et_industry)
	EditText industry;// 产业类型
	@Bind(R.id.et_salary)
	EditText salary;// 薪资
	@Bind(R.id.add_content)
	EditText AddContent;// 职位描述
	//发布按钮
	@Bind(R.id.btn_submit)
	Button btn_submit;
	
	private String filePath;//用户头像图像的本地地址
	private String photoPath;//拍摄头像照片的本地地址
	private String avatarUrl;//用户头像图像上传成功后，在服务器上的地址
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
		setHeaderTitle(headerView, "发布招聘");
		setHeaderImage(headerView, R.drawable.icon_findback_pre, Position.LEFT, new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				 finish();
			}
		});
	}

	@OnClick(R.id.btn_submit)
	public void doClick(View v) {
 		// 收集用户输入的内容，创建一个对象，保存到服务器
		Intent intent=getIntent();
		String name=intent.getStringExtra("com_user");
		Log.d("tedu", "这里是获取到用户名："+name);
		if (isEmpty(et_job, companyName, address, workTime, education, finance,
				people, industry, salary, AddContent)) {
			return;
		}

		// 判网
		if (!BmobNetUtil.isNetworkAvailable(this)) {
			toast("亲，网络不给力哦，请稍后再试。");
			return;
		}
		
		/*
		BmobQuery<CompanyUser> query=new BmobQuery<CompanyUser>();
		
		query.addWhereEqualTo("c_email", name);// 查询company表c_email字段中username
		BmobLog.d("tedu", this.getClass()+":"+name);
		query.findObjects(this, new FindListener<CompanyUser>() {
			
			@Override
			public void onSuccess(List<CompanyUser> object) {
				// 服务器完成了查询
				if (object.size() > 0) {
					companyUser=object.get(0);
					Log.d("tedu", "用户名："+companyUser.getC_email());
				}
			}
			@Override
			public void onError(int arg0, String arg1) {
				 BmobLog.d("tedu", this.getClass()+":"+arg0+":"+arg1);
				
			}
		});*/
		
		final CompanyInfo userinfo = new CompanyInfo();
		// 指定用户的头像的地址
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
				// 保存成功后做一些清理工作
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
				toast("发布成功！");
			}

			@Override
			public void onFailure(int errcode, String msg) {
				toast("发布失败");
				toastAndLog("错误信息：", errcode + ":" + msg);
				return;
			}
		});

	}

	@OnClick(R.id.iv_regist_avatar)
	public void setAvatar(View v){
		//创建Intent
		//创建打开系统图库的Intent
		Intent intent1 = new Intent(Intent.ACTION_PICK);
		intent1.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");

		//创建打开系统相机的Intnet
		Intent intent2 = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		//指定拍摄照片保存的位置
		File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES),System.currentTimeMillis()+".png");
		photoPath = file.getAbsolutePath();
		Uri imgUri = Uri.fromFile(file);
		intent2.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);

		//创建Intent选择器
		Intent chooser = Intent.createChooser(intent1, "请选择头像...");
		chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intent2});

		//发送intent
		startActivityForResult(chooser, 101);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if(resultCode==RESULT_OK && requestCode==101){
			if(data!=null){
				//用户是从图库选的图
				//用户选择了哪张图
				//该uri并不是图片在SD卡上的绝对路径
				//而是该图片在图库中的索引(content://xxxx/9)
				Uri uri = data.getData();
				//利用ContentResolver去图库中查询，uri对应图片在SD上的绝对路径
				Cursor c = getContentResolver().query(uri, new String[]{MediaStore.Images.Media.DATA}, null, null, null);
				c.moveToNext();
				filePath = c.getString(0);
				c.close();

			}else{
				//用户是从相机拍摄
				filePath = photoPath;
			}

			//上传图形到Bmob文件服务器
			BmobProFile.getInstance(this).upload(filePath, new UploadListener() {

				@Override
				public void onError(int arg0, String arg1) {
					Toast.makeText(ReleaseActivity.this, "头像上传失败，请稍后重试", Toast.LENGTH_SHORT).show();
					Log.d("TAG", "错误代码:"+arg0+","+arg1);
				}

				@Override
				public void onSuccess(String arg0, String arg1, BmobFile arg2) {
					Toast.makeText(ReleaseActivity.this, "头像上传成功", Toast.LENGTH_SHORT).show();
					//获得头像上传到服务器后，服务器上的保存地址
					avatarUrl = arg2.getUrl();
					//保证头像上传成功后，再将用户选择/拍照的头像图片方法到ivAvatar中显示
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

