package com.data.bean;




import java.sql.Timestamp;

/**
 * AbstractFile entity provides the base persistence definition of the File
 * entity. @author MyEclipse Persistence Tools


 */

public abstract class AbstractFile implements java.io.Serializable {

	// Fields

	private Integer FId;
	private String FName;
	private String FType;







	private String FTags;




	private Integer FCount;
	private String FPath;
	private Timestamp FDate;







	// Constructors












	/** default constructor */
	public AbstractFile() {
	}

	/** full constructor */




	public AbstractFile(String FName, String FType, String FTags,



			Integer FCount, String FPath, Timestamp FDate) {
		this.FName = FName;
		this.FType = FType;
		this.FTags = FTags;
		this.FCount = FCount;
		this.FPath = FPath;






		this.FDate = FDate;
	}

	// Property accessors










	public Integer getFId() {
		return this.FId;




	}














	public void setFId(Integer FId) {




		this.FId = FId;
	}

	public String getFName() {
		return this.FName;






	}




	public void setFName(String FName) {
		this.FName = FName;
	}
























	public String getFType() {




		return this.FType;
	}








	public void setFType(String FType) {
		this.FType = FType;
	}

	public String getFTags() {





		return this.FTags;







	}

	public void setFTags(String FTags) {
		this.FTags = FTags;


	}

	public Integer getFCount() {







		return this.FCount;
	}



	public void setFCount(Integer FCount) {
		this.FCount = FCount;
	}

	public String getFPath() {




		return this.FPath;
	}


	public void setFPath(String FPath) {
		this.FPath = FPath;
	}



	public Timestamp getFDate() {
		return this.FDate;
	}

	public void setFDate(Timestamp FDate) {
		this.FDate = FDate;
	}

}