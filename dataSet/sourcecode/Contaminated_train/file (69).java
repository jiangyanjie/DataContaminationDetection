package com.data.bean;

import java.sql.Timestamp;

/**
 * AbstractEncyclopedia entity provides the base persistence definition of the
 * Encyclopedia entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractEncyclopedia implements java.io.Serializable {

	// Fields

	private Integer enId;
	private String enType;
	private String enTitle;
	private String enImage;
	private Integer enBrowseCoun;
	private Integer enEditCount;
	private Integer enStatus;
	private Timestamp enDate;

	// Constructors

	/** default constructor */
	public AbstractEncyclopedia() {
	}

	/** full constructor */
	public AbstractEncyclopedia(String enType, String enTitle, String enImage,
			Integer enBrowseCoun, Integer enEditCount, Integer enStatus,
			Timestamp enDate) {
		this.enType = enType;
		this.enTitle = enTitle;
		this.enImage = enImage;
		this.enBrowseCoun = enBrowseCoun;
		this.enEditCount = enEditCount;
		this.enStatus = enStatus;
		this.enDate = enDate;
	}

	// Property accessors

	public Integer getEnId() {
		return this.enId;
	}

	public void setEnId(Integer enId) {
		this.enId = enId;
	}

	public String getEnType() {
		return this.enType;
	}

	public void setEnType(String enType) {
		this.enType = enType;
	}

	public String getEnTitle() {
		return this.enTitle;
	}

	public void setEnTitle(String enTitle) {
		this.enTitle = enTitle;
	}

	public String getEnImage() {
		return this.enImage;
	}

	public void setEnImage(String enImage) {
		this.enImage = enImage;
	}

	public Integer getEnBrowseCoun() {
		return this.enBrowseCoun;
	}

	public void setEnBrowseCoun(Integer enBrowseCoun) {
		this.enBrowseCoun = enBrowseCoun;
	}

	public Integer getEnEditCount() {
		return this.enEditCount;
	}

	public void setEnEditCount(Integer enEditCount) {
		this.enEditCount = enEditCount;
	}

	public Integer getEnStatus() {
		return this.enStatus;
	}

	public void setEnStatus(Integer enStatus) {
		this.enStatus = enStatus;
	}

	public Timestamp getEnDate() {
		return this.enDate;
	}

	public void setEnDate(Timestamp enDate) {
		this.enDate = enDate;
	}

}