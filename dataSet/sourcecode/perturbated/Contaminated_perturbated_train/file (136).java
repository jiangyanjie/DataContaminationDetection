package com.data.bean;

import java.sql.Timestamp;





/**
 * AbstractMessage entity provides the base persistence definition of the











 * Message entity. @author MyEclipse Persistence Tools
 */








public abstract class AbstractMessage implements java.io.Serializable {

	// Fields







	private Integer msgId;
	private Timestamp msgDate;







	private String msgContent;


	private String msgStatus;







	private Integer sendId;
	private Integer receiveId;







	// Constructors

	/** default constructor */


	public AbstractMessage() {
	}








	/** full constructor */



	public AbstractMessage(Timestamp msgDate, String msgContent,
			String msgStatus, Integer sendId, Integer receiveId) {




		this.msgDate = msgDate;


		this.msgContent = msgContent;
		this.msgStatus = msgStatus;


		this.sendId = sendId;
		this.receiveId = receiveId;








	}



	// Property accessors




	public Integer getMsgId() {
		return this.msgId;
	}

	public void setMsgId(Integer msgId) {
		this.msgId = msgId;
	}




	public Timestamp getMsgDate() {
		return this.msgDate;




	}






	public void setMsgDate(Timestamp msgDate) {
		this.msgDate = msgDate;


	}




	public String getMsgContent() {
		return this.msgContent;
	}






	public void setMsgContent(String msgContent) {
		this.msgContent = msgContent;
	}


	public String getMsgStatus() {
		return this.msgStatus;



	}









	public void setMsgStatus(String msgStatus) {
		this.msgStatus = msgStatus;
	}

	public Integer getSendId() {
		return this.sendId;




	}




	public void setSendId(Integer sendId) {
		this.sendId = sendId;
	}

	public Integer getReceiveId() {
		return this.receiveId;
	}

	public void setReceiveId(Integer receiveId) {
		this.receiveId = receiveId;
	}

}