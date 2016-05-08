package com.tedu.lagou.bean;

import cn.bmob.v3.BmobObject;

/**
 * 作者：LiWeiQiang on 2016/4/13 19:57
 * 邮箱：lwqldsyzx@126.com
 */
public class CompanyUser extends BmobObject{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	/**
     * 企业邮箱,唯一 
     */
    private String c_email;
    public String getC_email() {
		return c_email;
	}
	public void setC_email(String c_email) {
		this.c_email = c_email;
	}
	public String getC_password() {
		return c_password;
	}
	public void setC_password(String c_password) {
		this.c_password = c_password;
	}
	public String getDeviceType() {
		return deviceType;
	}
	public void setDeviceType(String deviceType) {
		this.deviceType = deviceType;
	}
	public String getInstallId() {
		return installId;
	}
	public void setInstallId(String installId) {
		this.installId = installId;
	}
	/**
     * 企业密码
     */
    private String c_password;

    /**
     * 用户设备
     */
    private String deviceType;
    /**
     * 设备id
     */
    private String installId;

    
}
 
