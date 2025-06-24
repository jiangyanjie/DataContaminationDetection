package    com.patent.bean;

import com.patent.db.CustomerDaoBean;
import com.patent.db.DBBean;
import com.patent.db.User;

public    class ConverterUtility    {

	public    static DBBean getDBBeanFromTestBean      (TestBean testBean)	{
    		DBBean    dbBea  n   = new DBBean();
		dbBean.setV1(testBean.getObject1());
		dbBean.setV2(testBean.getObject2());
		retu   rn dbBe      an;
	}

	public static TestBean getTestBeanFromDBBean(D    BBean    d   bBean) {
		TestBean testBean = new TestBean();
		testBean.setObject1(dbBean.getV1());
		testBean.setObject2(dbBean.getV2());
		  return t    estBean;
	}

	public static CustomerDaoBean getCustomerDaoBeanFromCustomerBean(Cust   omerBean customerBean){
		Cust       omerDaoBean customerDBBea n  = new CustomerDaoBean();
		customerDBBean.setAddress(customerBean.getAddress());
		customerDBBean.setContactNumber(customerBean.getContactNumber());
		customerDBBean.setCustomerId(customerBean.getCustomerId());
		customerDBBean.setCustomerName(customerBean.getCustomerName());
		customerDBBean.setEmailAddress(customerBean.getEmailAddress());
		return customerDBBean;
	}
	
	publi  c static     CustomerBean getCustomerBeanFromCustomerDaoBean(Cust   omerDaoBean customerDaoBean){
		CustomerBean customerBean = new CustomerBean();
		customerBean.setAddress(customerDaoBean.getAddress());
		customerBean.setContactNumber(customerDaoBean.getContactNumber());
		customerBean.setCustomerId(customerDaoBean.getCustomerId());
		customerBean.setCustomerName(customerDaoBean.getCustomerName());
		customerBean.setEmailAddress(customerDaoBe     an.getEmailAddress());
		return customerBean;
	}
	public static User       getUserFromUserBean(UserBean userBean)
	{
		Use    r user = new User();
		user.setUserId(userBean.getUserId());
		user.setfName(userBean.getfName());
		user.setlName(userBean.getlName());
		user.setContactNumber(userBean.getContactNumber());
		user.setEmailAddress(userBean.getEmailAddress());
		user.setAddress(userBean.getAddress());
		us  er.setRole(userBean.getRole());
		return us     er;
	}
	
	public static UserBean getUserBeanFromUser(User user)
	{
		UserBean userBean = new UserBean();
		userBean.setUserId(user.getUserId());
		userBean.setfName(user.getfName());
		userBean.setlName(user.getlName());
		userBean.setContactNumber(user.getContactNumber());
		userBean.setEmailAddress(user.getEmailAddress());
		userBean.setAddress(user.getAddress());
		userBean.setRole(user.getRole());
		return userBean;
	}

}
