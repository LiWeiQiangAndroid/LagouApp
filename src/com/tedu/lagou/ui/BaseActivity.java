package com.tedu.lagou.ui;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import cn.bmob.im.BmobChatManager;
import cn.bmob.im.BmobUserManager;
import cn.bmob.im.db.BmobDB;
import cn.bmob.im.util.BmobLog;
import cn.bmob.v3.listener.UpdateListener;

import com.tedu.lagou.R;
import com.tedu.lagou.app.MyApp;
import com.tedu.lagou.bean.User;

/**
 * @author by LiWeiQiang on 2016/4/11 20:04.
 * @Email��lwqldsyzx@126.com
 */
public class BaseActivity extends FragmentActivity {

	BaseActivity activity;
    public static final int LEFT = 0;
    public static final int RIGHT = 1;
    public static final int CENTER = 2;

    BmobUserManager bmobUserManager;
    BmobChatManager bmobChatManager;

    BmobDB bmobDB;

    Toast toast;

    enum Position {LEFT, CENTER, RIGHT}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        toast = Toast.makeText(this, "", Toast.LENGTH_LONG);
        bmobUserManager = BmobUserManager.getInstance(this);
        bmobChatManager = BmobChatManager.getInstance(this);
        bmobDB = BmobDB.create(this);

    }

    //Toast�����
    public void toast(final String text) {
        if (!TextUtils.isEmpty(text)) {
			runOnUiThread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					if (toast == null) {
						toast = Toast.makeText(getApplicationContext(), text,
								Toast.LENGTH_LONG);
					} else {
						toast.setText(text);
					}
					toast.show();
				}
			});
        }
    }
    //Log�����

    public void log(String log) {
        Log.d("tedu", this.getClass().getName() + "�����" + log);
        BmobLog.i(log);
    }

    //Toast��Log�����
    public void toastAndLog(String text, String log) {
        toast(text);
        log(log);
    }

  //�������ת
  	public void jump(Class<?> clazz,boolean isFinish){
  		Intent intent  = new Intent(this,clazz);
  		startActivity(intent);
  		if(isFinish){
  			this.finish();
  		}
  	}

  	public void jump(Intent intent,boolean isFinish){
  		startActivity(intent);
  		if(isFinish){
  			this.finish();
  		}
  	}
  	
  //����ͷ������
  	//����ͷ�����⣬���������ʾ
  	public void setHeaderTitle(View headerView,String text){
//  		TextView tv = (TextView) headerView.findViewById(R.id.tv_headerview_title);
//  		if(text==null){
//  			tv.setText("");
//  		}else{
//  			tv.setText(text);
//  		}
  		setHeaderTitle(headerView, text, Position.CENTER);
  	}
  	//����ͷ����������ط�����������ȷָ�������λ��
  	public void setHeaderTitle(View headerView,String text,Position pos){
  		TextView tv = (TextView) headerView.findViewById(R.id.tv_headerview_title);
  		//		switch (pos) {
  		//		case 0:
  		//			tv.setGravity(LEFT);
  		//			break;
  		//
  		//		default:
  		//			break;
  		//		}

  		switch (pos) {
  		case LEFT:
  			tv.setGravity(Gravity.LEFT);
  			break;
  		case RIGHT:
  			tv.setGravity(Gravity.RIGHT);
  			break;
  		case CENTER:
  			tv.setGravity(Gravity.CENTER);
  			break;
  		}
  		
  		if(text==null){
  			tv.setText("");
  		}else{
  			tv.setText(text);
  		}
  	}
  	/**
  	 * ��������ͷ�������Ҳ��ͼ��
  	 * @param headerView ͷ��headerview
  	 * @param resId ��ʾͼ�����Դid
  	 * @param pos pos�����LEFT����������ͼ�������CENTER��RIGHT��Ϊ�����Ҳ�ͼ��
  	 */
  	public void setHeaderImage(View headerView,int resId,Position pos,OnClickListener listener){
  		ImageView iv = null;
  		switch (pos) {
  		
  		case LEFT:
  			iv = (ImageView) headerView.findViewById(R.id.iv_haderview_left);
  			break;
  		
  		case CENTER:
  		case RIGHT:
  			iv = (ImageView) headerView.findViewById(R.id.iv_haderview_right);
  			break;
  		}
  		iv.setImageResource(resId);
  		iv.setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
  		
  		if(listener!=null){
  			iv.setOnClickListener(listener);
  		}
  	}
  //����ͷ������ ������ɫ
  	public void setHeaderTitleColor(View headerView,int resId){
  		TextView tv = (TextView) headerView.findViewById(R.id.tv_headerview_title);
  		tv.setTextColor(resId);
  	}

    /**
     * �п�
     * ����true����˵����EditTextδ��������
     * ����false����˵��EditText��������
     */
    public boolean isEmpty(EditText... editTexts) {
        for (EditText editText : editTexts) {
            if (TextUtils.isEmpty(editText.getText().toString())) {
//                editText.setError("������������Ϣ");
                editText.setError(Html.fromHtml("<font color=#ff0000>������!</font>"));
                return true;
            }
        }
        return false;
    }
    /**
	 * ����ָ���û���λ����Ϣ
	 * @param user
	 */
	public void updateUserLocation(User user) {
		//����һ���û���λ�ã���λ�����ó�MyApp.lastPoint
		//����˵�ǰ�ĵ�¼�û�
		//User user = bmobUserManager.getCurrentUser(User.class);
		//��������û�point���Ե�ֵ
		User user2 = new User();
		user2.setPoint(MyApp.lastPoint);
		user2.update(this, user.getObjectId(), new UpdateListener() {
			
			@Override
			public void onSuccess() {
				toast("λ�ø��³ɹ�");//???
			}
			
			@Override
			public void onFailure(int arg0, String arg1) {
				toastAndLog("λ�ø���ʧ��", arg0+": "+arg1);
			}
		});
	}
    /*
	 * �ж�email��ʽ�Ƿ���ȷ
	 */
    public boolean isEmail(String email) {

//		String str = "^w+[-+.]w+)*@w+([-.]w+)*.w+([-.]w+)*$";
        String str = "[\\w!#$%&'*+/=?^_`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^_`{|}~-]+)*@(?:[\\w](?:[\\w-]*[\\w])?\\.)+[\\w](?:[\\w-]*[\\w])?";

//		String str = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
        Pattern p = Pattern.compile(str);
        Matcher m = p.matcher(email);
        return m.matches();
    }

    public boolean isMobileNO(String mobiles){
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0-9])|(170))\\d{8}$");
        Matcher m = p.matcher(mobiles);
        log(mobiles+"is"+m.matches());
        return m.matches();
    }
    public void showDialog(){
    	new AlertDialog.Builder(this)
    	.setTitle(
    			this.getResources().getString(
    					R.string.dialog_message_title))
    	.setMessage(
    			this.getResources().getString(
    					R.string.error_aialog))
    	.setNegativeButton(android.R.string.ok,
    			new DialogInterface.OnClickListener() {
    				public void onClick(DialogInterface dialog,
    						int which) {
    					dialog.dismiss();
    				}
    			}).show();
    	}
    
}
