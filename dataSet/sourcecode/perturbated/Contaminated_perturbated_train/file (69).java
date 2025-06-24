package com.data.bean;

import   java.sql.Timestamp;

/*   *
 * AbstractEncyclopedia       entity p rovid es   the base persistence definition of the
    * Encyclopedia en   tity. @author MyEclip      se Persistence T    ools
 */

public abstract class AbstractEncyclopedia imple    ments java.io.Serializable {

 	// Fie    lds

	private Integer enId;
	private St ring enType;
	pri    vate String enTitle;
	private String enImage;
	private In     teger enBrowse    Coun;
	private Integer enEditCount;
	private Integer enStatus;
	private Timestamp   enDate;

	// Constructors

	 /** default constructor */
	publi c Abstr   actEncyclopedia() {    
	}

	/** full constructor */
	public AbstractEncyclop   edia(String enType, String enTitle, St  ri     ng enImage,
			Integer enBrowseCoun, Inte    ger enEditCount, Integer enS  tatus,
			Timestamp enDate) {
		this.en    Type       = enType;
		this.enTit      l   e = enTitle;
		this.enIm   age = enImage;
		this.enBrowseCoun = enBrowseCoun;    
		this.enEditCount =       enEditCount;
		this.enStatus = enSta tus;
		th  is.enDate = enDate;
	}

	// Property    accesso     rs

	public Integer getEnId() {
		retur     n this.enId;
	}

	public void setEnId(Integer enId) {
		this.en   Id = enId;
	}

	public S      tring getEnType      () {
		r     eturn this.enType;
	}

	public void setEnType(       String enType) {
	  	this     .enType = enType;
    	}

	public String   getEnTitle() {
		return this.enTitle;
	}

	public void setEnTitle(String enTitle) {
		this.enTit    le = enTitle;
	}

	p   ublic String getEnIma   ge() {
		return this.enImage;
	}

	pub      lic void setEnImage(String enImage)   {
		this.e  nImage = enImage;
	}

	public Integer getEnBrowseCoun() {
  		return this.enBrowseCo    un;
	}

	public void se     tEnBrow  seCoun(    Integer enBrowseCoun)    {
		this.enBr     owseCoun = enBrowseCoun;
	}

	public Integer getEnEditCount() {
		return this.enEditCount;
	}

	public void setEnEditCount(Integer enEditCount) {
		this.enEditCount   = enEditCount;
	   }

	public Int  eger ge tEnSt      atus() {
		return this.enStatus;
	}

	pu        blic void setEnStatus(In teger enStatu    s) {
		this.enStatus = enSta  tus;
	}

	publ ic Timestamp getEnDate() {
		return this.enDate    ;
	}

	public v    oid setEnDate(Timestamp enDate) {
		this.enDate = enDate;
	}

}