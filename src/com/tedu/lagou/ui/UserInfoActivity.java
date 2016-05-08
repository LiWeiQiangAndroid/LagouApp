package com.tedu.lagou.ui;

import java.io.File;
import java.util.List;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

import com.bmob.BmobProFile;
import com.bmob.btp.callback.UploadListener;
import com.tedu.lagou.R;
import com.tedu.lagou.bean.User;
import com.tedu.lagou.util.ImageLoader;

public class UserInfoActivity extends BaseActivity {
	ImageLoader imageLoader;
	// ͷ��
	@Bind(R.id.iv_userinfo_avatar)
	ImageView avatar;
	// ͷ��༭
	@Bind(R.id.iv_userinfo_avatar_edit)
	ImageView iv_userinfo_avatar_edit;
	// �ǳ�
	@Bind(R.id.tv_userinfo_nickname)
	TextView tv_userinfo_nickname;
	// �سƱ༭
	@Bind(R.id.iv_userinfo_nickname_edit)
	ImageView iv_userinfo_nickname_edit;

	@Bind(R.id.tv_userinfo_username)
	TextView tv_userinfo_username;
	
	@Bind(R.id.et_userinfo_nickname)
	EditText et_userinfo_nickname;
	
	// ��������
	@Bind(R.id.btn_userinfo_update)
	Button btn_userinfo_update;
	
	@Bind(R.id.ll_userinfo_edit_nickname_container)
	View edit_nickname_container;
	
	//�޸��ǳơ�ȷ��
	@Bind(R.id.btn_userinfo_nickname_confirm)
	Button nickname_confirm;
	//�޸�ȡ��
	@Bind(R.id.btn_userinfo_nickname_cancel)
	Button nickname_cancel;

	private static final int IMAGE_REQUEST_CODE = 0;
	private static final int CAMERA_REQUEST_CODE = 1;
	private static final int RESIZE_REQUEST_CODE = 2;

	private static final String IMAGE_FILE_NAME = "header.jpg";

	private String[] items = new String[] { "ѡ�񱾵�ͼƬ", "����" };
	private String avatarUrl;// �û�ͷ��ͼ���ϴ��ɹ����ڷ������ϵĵ�ַ
	private String filePath;// �û�ͷ��ͼ��ı��ص�ַ

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_user_info);
		ButterKnife.bind(this);
		initViews();

	}

	private void initViews() {
		String username = bmobUserManager.getCurrentUserName();
		tv_userinfo_username.setText(username);
		BmobQuery<User> query=new BmobQuery<User>();
		query.findObjects(this, new FindListener<User>() {
			
			@Override
			public void onSuccess(List<User> object) {
				if (object.get(0).getUrl()!=null) {
					imageLoader.DisplayImage(object.get(0).getUrl(), avatar);
				}else {
					avatar.setImageResource(R.drawable.icon_about_lagouman);
				}
				if (object.get(0).getNickname()!=null) {
					tv_userinfo_nickname.setText(object.get(0).getNickname());
				}
				
			}
			
			@Override
			public void onError(int arg0, String arg1) {
				 
				
			}
		});
					
	}

	@OnClick(R.id.iv_userinfo_avatar_edit)
	public void doClick(View v) {
		show();
	}
	
	@OnClick({R.id.btn_userinfo_update,R.id.iv_userinfo_nickname_edit,R.id.btn_userinfo_nickname_confirm,
		R.id.btn_userinfo_nickname_cancel})
	public void onClick(View v){
		switch (v.getId()) {
		case R.id.btn_userinfo_update: //����
			 User user=(User) bmobUserManager.getCurrentUser();

			user.setNickname(tv_userinfo_nickname.getText().toString());
			if (avatarUrl!=null) {
				user.setUrl(avatarUrl);
			}
			user.update(this, new UpdateListener() {
				
				@Override
				public void onSuccess() {
					 toast("���³ɹ���");
					
				}
				
				@Override
				public void onFailure(int arg0, String arg1) {
					toast("����ʧ��,���Ժ����ԣ�");
					
				}
			});
			
			break;
		case R.id.iv_userinfo_nickname_edit: //�༭�ǳ�
			String name =tv_userinfo_nickname.getText().toString();
			iv_userinfo_nickname_edit.setVisibility(View.GONE);//����
			tv_userinfo_nickname.setVisibility(View.GONE);
			edit_nickname_container.setVisibility(View.VISIBLE);
			et_userinfo_nickname.setText(name);
			break;
		case R.id.btn_userinfo_nickname_confirm: //ȷ��
			tv_userinfo_nickname.setText(et_userinfo_nickname.getText().toString());
			edit_nickname_container.setVisibility(View.GONE);
			iv_userinfo_nickname_edit.setVisibility(View.VISIBLE); //��ʾ
			tv_userinfo_nickname.setVisibility(View.VISIBLE);
			break;
		case R.id.btn_userinfo_nickname_cancel:
			edit_nickname_container.setVisibility(View.GONE);
			iv_userinfo_nickname_edit.setVisibility(View.VISIBLE); //��ʾ
			tv_userinfo_nickname.setVisibility(View.VISIBLE);
			break;
		} 
	}
	private void show() {
		new AlertDialog.Builder(this)
			.setTitle("����ͷ��")
			.setItems(items, new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface dialog, int which) {
					switch (which) {
					case 0:
						Intent galleryIntent = new Intent(
								Intent.ACTION_GET_CONTENT);
						galleryIntent.addCategory(Intent.CATEGORY_OPENABLE);
						galleryIntent.setType("image/*");
						startActivityForResult(galleryIntent,
								IMAGE_REQUEST_CODE);
						break;
					case 1:
						if (isSdcardExisting()) {
							Intent cameraIntent = new Intent(
									"android.media.action.IMAGE_CAPTURE");
							cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,
									getImageUri());
							cameraIntent.putExtra(
									MediaStore.EXTRA_VIDEO_QUALITY, 0);
							startActivityForResult(cameraIntent,
									CAMERA_REQUEST_CODE);
						} else {
							Toast.makeText(UserInfoActivity.this, "�����sd��",
									Toast.LENGTH_LONG).show();
						}
						break;
					}
				}
			})
				.setNegativeButton("ȡ��", new DialogInterface.OnClickListener() {

					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.dismiss();
					}
				}).show();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode != RESULT_OK) {
			return;
		} else {
			switch (requestCode) {
			case IMAGE_REQUEST_CODE:
				resizeImage(data.getData());
				break;
			case CAMERA_REQUEST_CODE:
				if (isSdcardExisting()) {
					resizeImage(getImageUri());
				} else {
					Toast.makeText(UserInfoActivity.this, "δ�ҵ��洢�����޷��洢��Ƭ��",
							Toast.LENGTH_LONG).show();
				}
				break;

			case RESIZE_REQUEST_CODE:
				if (data != null) {
					showResizeImage(data);
				}
				break;
			}
		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	private boolean isSdcardExisting() {
		final String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			return true;
		} else {
			return false;
		}
	}

	public void resizeImage(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		intent.putExtra("crop", "true");
		intent.putExtra("aspectX", 1);
		intent.putExtra("aspectY", 1);
		intent.putExtra("outputX", 150);
		intent.putExtra("outputY", 150);
		intent.putExtra("return-data", true);
		filePath=getImageAbsolutePath(this,uri);
		log("resizeImage(Uri)·����"+filePath);
		BmobProFile.getInstance(this).upload(filePath, new UploadListener() {
			
			@Override
			public void onError(int arg0, String arg1) {
				Toast.makeText(UserInfoActivity.this, "ͷ���ϴ�ʧ�ܣ����Ժ�����", Toast.LENGTH_SHORT).show();
				Log.d("TAG", "�������:"+arg0+","+arg1);
			}
			
			@Override
			public void onSuccess(String arg0, String arg1, BmobFile arg2) {
				Toast.makeText(UserInfoActivity.this, "ͷ���ϴ��ɹ�,��������������Ч", Toast.LENGTH_LONG).show();
				//���ͷ���ϴ����������󣬷������ϵı����ַ
				avatarUrl = arg2.getUrl();
				log("UserInfoActivity-->url"+avatarUrl);
				//��֤ͷ���ϴ��ɹ����ٽ��û�ѡ��/���յ�ͷ��ͼƬ������ivAvatar����ʾ
				avatar.setImageBitmap(BitmapFactory.decodeFile(filePath));
			}
			
			@Override
			public void onProgress(int arg0) {
				
			}
		});
		
		startActivityForResult(intent, RESIZE_REQUEST_CODE);
	}

	private void showResizeImage(Intent data) {
		Bundle extras = data.getExtras();
		if (extras != null) {
			Bitmap photo = extras.getParcelable("data");
//			Drawable drawable = new BitmapDrawable(photo);
			avatar.setImageBitmap(photo);
//			avatar.setImageDrawable(drawable);
		}
	}

	private Uri getImageUri() {
		return Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
				IMAGE_FILE_NAME));
	}
	/**
	 * ����Uri��ȡͼƬ����·�������Android4.4���ϰ汾Uriת��
	 */
	@TargetApi(19)
	public static String getImageAbsolutePath(Activity context, Uri imageUri) {
		if (context == null || imageUri == null)
			return null;
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.KITKAT && DocumentsContract.isDocumentUri(context, imageUri)) {
			if (isExternalStorageDocument(imageUri)) {
				String docId = DocumentsContract.getDocumentId(imageUri);
				String[] split = docId.split(":");
				String type = split[0];
				if ("primary".equalsIgnoreCase(type)) {
					return Environment.getExternalStorageDirectory() + "/" + split[1];
				}
			} else if (isDownloadsDocument(imageUri)) {
				String id = DocumentsContract.getDocumentId(imageUri);
				Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
				return getDataColumn(context, contentUri, null, null);
			} else if (isMediaDocument(imageUri)) {
				String docId = DocumentsContract.getDocumentId(imageUri);
				String[] split = docId.split(":");
				String type = split[0];
				Uri contentUri = null;
				if ("image".equals(type)) {
					contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
				} else if ("video".equals(type)) {
					contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
				} else if ("audio".equals(type)) {
					contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
				}
				String selection = MediaStore.Images.Media._ID + "=?";
				String[] selectionArgs = new String[] { split[1] };
				return getDataColumn(context, contentUri, selection, selectionArgs);
			}
		} // MediaStore (and general)
		else if ("content".equalsIgnoreCase(imageUri.getScheme())) {
			// Return the remote address
			if (isGooglePhotosUri(imageUri))
				return imageUri.getLastPathSegment();
			return getDataColumn(context, imageUri, null, null);
		}
		// File
		else if ("file".equalsIgnoreCase(imageUri.getScheme())) {
			return imageUri.getPath();
		}
		return null;
	}

	public static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
		Cursor cursor = null;
		String column = MediaStore.Images.Media.DATA;
		String[] projection = { column };
		try {
			cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
			if (cursor != null && cursor.moveToFirst()) {
				int index = cursor.getColumnIndexOrThrow(column);
				return cursor.getString(index);
			}
		} finally {
			if (cursor != null)
				cursor.close();
		}
		return null;
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is ExternalStorageProvider.
	 */
	public static boolean isExternalStorageDocument(Uri uri) {
		return "com.android.externalstorage.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is DownloadsProvider.
	 */
	public static boolean isDownloadsDocument(Uri uri) {
		return "com.android.providers.downloads.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is MediaProvider.
	 */
	public static boolean isMediaDocument(Uri uri) {
		return "com.android.providers.media.documents".equals(uri.getAuthority());
	}

	/**
	 * @param uri The Uri to check.
	 * @return Whether the Uri authority is Google Photos.
	 */
	public static boolean isGooglePhotosUri(Uri uri) {
		return "com.google.android.apps.photos.content".equals(uri.getAuthority());
	}

}