package com.data.bean;

/**
 * AbstractUserEnId entity provides the base persistence definition of the
 * UserEnId entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractUserEnId implements java.io.Serializable {

	// Fields

	private Integer userId;
	private Integer enId;

	// Constructors

	/** default constructor */
	public AbstractUserEnId() {
	}

	/** full constructor */
	public AbstractUserEnId(Integer userId, Integer enId) {
		this.userId = userId;
		this.enId = enId;
	}

	// Property accessors

	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getEnId() {
		return this.enId;
	}

	public void setEnId(Integer enId) {
		this.enId = enId;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof AbstractUserEnId))
			return false;
		AbstractUserEnId castOther = (AbstractUserEnId) other;

		return ((this.getUserId() == castOther.getUserId()) || (this
				.getUserId() != null && castOther.getUserId() != null && this
				.getUserId().equals(castOther.getUserId())))
				&& ((this.getEnId() == castOther.getEnId()) || (this.getEnId() != null
						&& castOther.getEnId() != null && this.getEnId()
						.equals(castOther.getEnId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getUserId() == null ? 0 : this.getUserId().hashCode());
		result = 37 * result
				+ (getEnId() == null ? 0 : this.getEnId().hashCode());
		return result;
	}

}