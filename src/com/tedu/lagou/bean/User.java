package com.tedu.lagou.bean;

import cn.bmob.im.bean.BmobChatUser;
import cn.bmob.v3.datatype.BmobGeoPoint;

/**
 * 作者：LiWeiQiang on 2016/4/12 00:16
 * 邮箱：lwqldsyzx@126.com
 */
public class User extends BmobChatUser {
	
	private static final long serialVersionUID = 1L;

	BmobGeoPoint point;
    /*
     * 用户简历
     */
    private String resume;
    
    String url;//头像地址

    String nickname;
    
    
    public String getResume() {
		return resume;
	}

	public void setResume(String resume) {
		this.resume = resume;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

    public BmobGeoPoint getPoint() {
        return point;
    }

    public void setPoint(BmobGeoPoint point) {
        this.point = point;
    }
}
