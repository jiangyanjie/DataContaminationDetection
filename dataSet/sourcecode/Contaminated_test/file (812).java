/**
 * 
 */
package com.travelman.action.user;

import com.opensymphony.xwork2.ActionSupport;
import com.travelman.business.service.user.UserBusinesService;
import com.travelman.domain.User;

/**
 * @author Armaan
 *
 */
public class DeleteUserDataAction extends ActionSupport {
	
	private static final long serialVersionUID = 1L;
	private String fname;
	/**
	 * 
	 */
	private String lname;
	/**
	 * 
	 */
	private String uemail;
	/**
	 * 
	 */
	private String umobile;
	/**
	 * 
	 */
	private String haddress;
	/**
	 * 
	 */
	private String baddress;
	/**
	 * 
	 */
	private String utype;

	// Business Service
	private UserBusinesService userBusinesService;

	/**
	 * 
	 * @return
	 */

	public UserBusinesService getUserBusinesService() {
		return userBusinesService;
	}

	/**
	 * 
	 * @param userBusinesService
	 */
	public void setUserBusinesService(UserBusinesService userBusinesService) {
		this.userBusinesService = userBusinesService;
	}

	@Override
	public String execute() throws Exception {
		User user = new User();
		user.setUserId(1);
		user.setFname(fname);
		user.setLname(lname);
		user.setBaddress(baddress);
		user.setHaddress(haddress);
		user.setUemail(uemail);
		user.setUmobile(umobile);
		user.setUtype(utype);

		userBusinesService.removeUser(user);

		return SUCCESS;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getUemail() {
		return uemail;
	}

	public void setUemail(String uemail) {
		this.uemail = uemail;
	}

	public String getUmobile() {
		return umobile;
	}

	public void setUmobile(String umobile) {
		this.umobile = umobile;
	}

	public String getHaddress() {
		return haddress;
	}

	public void setHaddress(String haddress) {
		this.haddress = haddress;
	}

	public String getBaddress() {
		return baddress;
	}

	public void setBaddress(String baddress) {
		this.baddress = baddress;
	}

	public String getUtype() {
		return utype;
	}

	public void setUtype(String utype) {
		this.utype = utype;
	}
}


