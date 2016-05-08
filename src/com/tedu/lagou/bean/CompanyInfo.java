package com.tedu.lagou.bean;


import cn.bmob.v3.BmobObject;

public class CompanyInfo extends BmobObject {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public CompanyUser companyUser;
	/**
     * ��ҵ����,Ψһ��ʶ
     */
    private String c_email;
     
    /**
     * ��ҵlogo url��ַ
     */
    
    private String url;
    /**
     * ��Ƹְλ
     */
    private String job;

    /**
     * ��ҵ�ù�˾��
     */
    private String companyName;

    /**
     * ��ҵ��ַ
     */
    private String address;
    /**
     * Ҫ����
     */
    private String workTime;
    /**
     * Ҫ��ѧ��
     */
    private String education;
    /**
     * ���й�˾�����ʡ�����Ҫ������
     */
    private String finance;
    /**
     * ��ҵ����
     */
    private String industry;
    /**
     * ��˾����
     */
    private String people;
    /**
     * ����ʱ��
     */
    private String addTime;
    /**
     * н��
     */
    private String salary;
    /**
     * ְλ����
     * @return
     */
    private String content;

    /**
     * ����ʱ��
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
