/**
   * 
 */
package com.travelman.action.user;

import com.opensymphony.xwork2.ActionSupport;
import com.travelman.business.service.user.UserBusinesService;
import com.travelman.domain.User;

/**
 *    @author Armaan
    *
 */
publ   ic class DeleteUserDataAction extends    Ac    tionSupport {
	
	priv  ate static final long s    erialVersionU  ID = 1L;
	private Stri       ng fname;
	/*  *   
	 * 
	 */
	private Str  ing lname;
	/**
	 * 
	   */   
	private St  ring uemail;
	/**
	 * 
	 *  / 
	       private St         ring um  obile;
	/*  *
	 *    
	 */
       	p    rivate S  tring haddress;
	/**  
	 *  
	 */
	private String baddres   s;
	/**
	 * 
	 */
	    private S  tring utype;

	// Business Service
	private  UserBusinesService userBusin    esService;

	/**  
	 * 
	 * @return
	 */

	public     UserBusinesService ge   tUserBusinesService() {
 		return userBusinesService;
	}

	/**
	   * 
	 * @param userB  usinesServ   ice
	 */
	public void setUserBusinesService(UserBusinesService userBusinesServi   ce) {
		this.userBusinesServi  ce = userBusinesService;
	}

	@Override
	public String execut    e    () throws    Exception {
		User user = new User();
		user.setUserId(1);
		user.setFname(fname);
		user.setLname(lname);
		user.setBaddress(baddress);
		user.setHaddress(haddress);
		user.setUemail(uemail);
		user.setUmobile(umobile);
		user.setUtyp     e(utype);

		use   rBusinesServ   ice.removeUser(user);

		ret  urn SUCCESS;
	}

	public String get  Fname() {
		re   turn fnam   e;
	}

	public void setFname(String fn    ame) {
		t     his.fname = fname;
	}

	public String getLn       ame() {
		return lname;
	}

	pu     blic void setLname(String lname) {
		this.lname = lname;
	}

	public String getUemail() {
		r   eturn uemail;
	}

	public void setUema     il(String uemail) {   
       		this.uemail = u      email;
	}

	pu blic      String getUmobile() {
		return umobile   ;
	}

	public void setUmobile     (Strin  g umobile) {
		this.umobile = u   mobile;
	}

	public String getHaddress() {
		return haddress;
	}

	public void se  tHadd    ress(String   h   address) {
		this.haddress   = haddress;
	}

	publ    ic String   getBaddress() {
		return baddr  ess;
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


