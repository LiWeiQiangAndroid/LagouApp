package com.tedu.lagou.bean;


import cn.bmob.v3.BmobObject;

public class CompanyInfo extends BmobObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CompanyUser companyUser;
	/**
     * 企业邮箱,唯一标识
     */
    private String c_email;
     
    /**
     * 企业logo url地址
     */
    
    private String url;
    /**
     * 招聘职位
     */
    private String job;

    /**
     * 企业用公司名
     */
    private String companyName;

    /**
     * 企业地址
     */
    private String address;
    /**
     * 要求经验
     */
    private String workTime;
    /**
     * 要求学历
     */
    private String education;
    /**
     * 上市公司、融资、不需要用融资
     */
    private String finance;
    /**
     * 产业类型
     */
    private String industry;
    /**
     * 公司人数
     */
    private String people;
    /**
     * 发布时间
     */
    private String addTime;
    /**
     * 薪资
     */
    private String salary;
    /**
     * 职位描述
     * @return
     */
    private String content;

    /**
     * 发布时间
     */
    private String date;

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public CompanyUser getCompanyUser() {
		return companyUser;
	}

	public void setCompanyUser(CompanyUser companyUser) {
		this.companyUser = companyUser;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getC_email() {
        return c_email;
    }

    public void setC_email(String c_email) {
        this.c_email = c_email;
    }


    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getWorkTime() {
        return workTime;
    }

    public void setWorkTime(String workTime) {
        this.workTime = workTime;
    }

    public String getEducation() {
        return education;
    }

    public void setEducation(String education) {
        this.education = education;
    }

    public String getFinance() {
        return finance;
    }

    public void setFinance(String finance) {
        this.finance = finance;
    }

    public String getIndustry() {
        return industry;
    }

    public void setIndustry(String industry) {
        this.industry = industry;
    }

    public String getPeople() {
        return people;
    }

    public void setPeople(String people) {
        this.people = people;
    }

    public String getAddTime() {
        return addTime;
    }

    public void setAddTime(String addTime) {
        this.addTime = addTime;
    }

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }
}
