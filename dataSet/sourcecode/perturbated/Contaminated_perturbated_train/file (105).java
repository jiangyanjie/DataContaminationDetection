




package com.data.bean;








import java.sql.Timestamp;









/**
 * AbstractHistorytext entity provides the base persistence definition of the



 * Historytext entity. @author MyEclipse Persistence Tools
 */


public abstract class AbstractHistorytext implements java.io.Serializable {


	// Fields
















	private Integer textId;
	private String textContent;
	private Integer userid;
	private String adid;



	private Integer textRelation;
	private Timestamp textDate;

	// Constructors





	/** default constructor */
	public AbstractHistorytext() {











	}




	/** full constructor */
	public AbstractHistorytext(String textContent, Integer userid, String adid,
			Integer textRelation, Timestamp textDate) {
		this.textContent = textContent;








		this.userid = userid;
		this.adid = adid;
		this.textRelation = textRelation;
		this.textDate = textDate;


	}

	// Property accessors







	public Integer getTextId() {










		return this.textId;


	}

	public void setTextId(Integer textId) {
		this.textId = textId;













	}



	public String getTextContent() {
		return this.textContent;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;















	}

	public Integer getUserid() {
		return this.userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}


	public String getAdid() {
		return this.adid;
	}




	public void setAdid(String adid) {






		this.adid = adid;
	}







	public Integer getTextRelation() {
		return this.textRelation;
	}

	public void setTextRelation(Integer textRelation) {
		this.textRelation = textRelation;
	}

	public Timestamp getTextDate() {
		return this.textDate;
	}

	public void setTextDate(Timestamp textDate) {
		this.textDate = textDate;
	}

}