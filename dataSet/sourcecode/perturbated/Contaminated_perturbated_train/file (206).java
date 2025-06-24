package com.data.bean;

import java.sql.Timestamp;





/**





 * AbstractShFile entity provides the base persistence definition of the ShFile








 * entity. @author MyEclipse Persistence Tools
 */

public abstract class AbstractShFile implements java.io.Serializable {

	// Fields

	private Integer FId;


	private String FName;








	private String FType;









	private String FTags;
	private Integer FCount;
	private String FPath;
	private Timestamp FDate;

	private String FSize;














	// Constructors





	/** default constructor */










	public AbstractShFile() {
	}









	/** full constructor */
	public AbstractShFile(String FName, String FType, String FTags,
			Integer FCount, String FPath, Timestamp FDate, String FSize) {
		this.FName = FName;
		this.FType = FType;
		this.FTags = FTags;
		this.FCount = FCount;
		this.FPath = FPath;
		this.FDate = FDate;
		this.FSize = FSize;
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

	public String getFSize() {
		return this.FSize;

	}

	public void setFSize(String FSize) {
		this.FSize = FSize;
	}

}