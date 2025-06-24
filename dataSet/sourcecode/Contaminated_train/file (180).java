package com.data.bean;

import java.sql.Timestamp;

/**
 * AbstractQuestion entity provides the base persistence definition of the
 * Question entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractQuestion implements java.io.Serializable {

	// Fields

	private Integer quId;
	private String quType;
	private String quContent;
	private String quStatus;
	private Timestamp quTime;
	private Timestamp quCloseTime;
	private String quTitle;

	// Constructors

	/** default constructor */
	public AbstractQuestion() {
	}

	/** full constructor */
	public AbstractQuestion(String quType, String quContent, String quStatus,
			Timestamp quTime, Timestamp quCloseTime, String quTitle) {
		this.quType = quType;
		this.quContent = quContent;
		this.quStatus = quStatus;
		this.quTime = quTime;
		this.quCloseTime = quCloseTime;
		this.quTitle = quTitle;
	}

	// Property accessors

	public Integer getQuId() {
		return this.quId;
	}

	public void setQuId(Integer quId) {
		this.quId = quId;
	}

	public String getQuType() {
		return this.quType;
	}

	public void setQuType(String quType) {
		this.quType = quType;
	}

	public String getQuContent() {
		return this.quContent;
	}

	public void setQuContent(String quContent) {
		this.quContent = quContent;
	}

	public String getQuStatus() {
		return this.quStatus;
	}

	public void setQuStatus(String quStatus) {
		this.quStatus = quStatus;
	}

	public Timestamp getQuTime() {
		return this.quTime;
	}

	public void setQuTime(Timestamp quTime) {
		this.quTime = quTime;
	}

	public Timestamp getQuCloseTime() {
		return this.quCloseTime;
	}

	public void setQuCloseTime(Timestamp quCloseTime) {
		this.quCloseTime = quCloseTime;
	}

	public String getQuTitle() {
		return this.quTitle;
	}

	public void setQuTitle(String quTitle) {
		this.quTitle = quTitle;
	}

}