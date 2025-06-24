package com.data.bean;

import java.util.Date;

/**
 * AbstractUser entity provides the base persistence definition of the User
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUser implements java.io.Serializable {

	// Fields

	private Integer UId;
	private String UName;
	private String UPassword;
	private Date UBirthday;
	private String UGender;
	private String UImage;
	private String UEmail;

	// Constructors

	/** default constructor */
	public AbstractUser() {
	}

	/** full constructor */
	public AbstractUser(String UName, String UPassword, Date UBirthday,
			String UGender, String UImage, String UEmail) {
		this.UName = UName;
		this.UPassword = UPassword;
		this.UBirthday = UBirthday;
		this.UGender = UGender;
		this.UImage = UImage;
		this.UEmail = UEmail;
	}

	// Property accessors

	public Integer getUId() {
		return this.UId;
	}

	public void setUId(Integer UId) {
		this.UId = UId;
	}

	public String getUName() {
		return this.UName;
	}

	public void setUName(String UName) {
		this.UName = UName;
	}

	public String getUPassword() {
		return this.UPassword;
	}

	public void setUPassword(String UPassword) {
		this.UPassword = UPassword;
	}

	public Date getUBirthday() {
		return this.UBirthday;
	}

	public void setUBirthday(Date UBirthday) {
		this.UBirthday = UBirthday;
	}

	public String getUGender() {
		return this.UGender;
	}

	public void setUGender(String UGender) {
		this.UGender = UGender;
	}

	public String getUImage() {
		return this.UImage;
	}

	public void setUImage(String UImage) {
		this.UImage = UImage;
	}

	public String getUEmail() {
		return this.UEmail;
	}

	public void setUEmail(String UEmail) {
		this.UEmail = UEmail;
	}

}