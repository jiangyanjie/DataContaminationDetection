package com.data.bean;

/**
 * AbstractEnReId entity provides the base persistence definition of the EnReId
 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractEnReId implements java.io.Serializable {

	// Fields

	private Integer reId;
	private Integer enId;

	// Constructors

	/** default constructor */
	public AbstractEnReId() {
	}

	/** full constructor */
	public AbstractEnReId(Integer reId, Integer enId) {
		this.reId = reId;
		this.enId = enId;
	}

	// Property accessors

	public Integer getReId() {
		return this.reId;
	}

	public void setReId(Integer reId) {
		this.reId = reId;
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
		if (!(other instanceof AbstractEnReId))
			return false;
		AbstractEnReId castOther = (AbstractEnReId) other;

		return ((this.getReId() == castOther.getReId()) || (this.getReId() != null
				&& castOther.getReId() != null && this.getReId().equals(
				castOther.getReId())))
				&& ((this.getEnId() == castOther.getEnId()) || (this.getEnId() != null
						&& castOther.getEnId() != null && this.getEnId()
						.equals(castOther.getEnId())));
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getReId() == null ? 0 : this.getReId().hashCode());
		result = 37 * result
				+ (getEnId() == null ? 0 : this.getEnId().hashCode());
		return result;
	}

}