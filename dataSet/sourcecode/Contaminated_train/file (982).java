package com.patent.bean;

import com.patent.db.CustomerDaoBean;
import com.patent.db.DBBean;
import com.patent.db.User;

public class ConverterUtility {

	public static DBBean getDBBeanFromTestBean(TestBean testBean)	{
		DBBean dbBean = new DBBean();
		dbBean.setV1(testBean.getObject1());
		dbBean.setV2(testBean.getObject2());
		return dbBean;
	}

	public static TestBean getTestBeanFromDBBean(DBBean dbBean) {
		TestBean testBean = new TestBean();
		testBean.setObject1(dbBean.getV1());
		testBean.setObject2(dbBean.getV2());
		return testBean;
	}

	public static CustomerDaoBean getCustomerDaoBeanFromCustomerBean(CustomerBean customerBean){
		CustomerDaoBean customerDBBean = new CustomerDaoBean();
		customerDBBean.setAddress(customerBean.getAddress());
		customerDBBean.setContactNumber(customerBean.getContactNumber());
		customerDBBean.setCustomerId(customerBean.getCustomerId());
		customerDBBean.setCustomerName(customerBean.getCustomerName());
		customerDBBean.setEmailAddress(customerBean.getEmailAddress());
		return customerDBBean;
	}
	
	public static CustomerBean getCustomerBeanFromCustomerDaoBean(CustomerDaoBean customerDaoBean){
		CustomerBean customerBean = new CustomerBean();
		customerBean.setAddress(customerDaoBean.getAddress());
		customerBean.setContactNumber(customerDaoBean.getContactNumber());
		customerBean.setCustomerId(customerDaoBean.getCustomerId());
		customerBean.setCustomerName(customerDaoBean.getCustomerName());
		customerBean.setEmailAddress(customerDaoBean.getEmailAddress());
		return customerBean;
	}
	public static User getUserFromUserBean(UserBean userBean)
	{
		User user = new User();
		user.setUserId(userBean.getUserId());
		user.setfName(userBean.getfName());
		user.setlName(userBean.getlName());
		user.setContactNumber(userBean.getContactNumber());
		user.setEmailAddress(userBean.getEmailAddress());
		user.setAddress(userBean.getAddress());
		user.setRole(userBean.getRole());
		return user;
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
