package id.co.skyforce.basicjsf.controller;

import id.co.skyforce.basicjsf.HibernateUtil;
import id.co.skyforce.basicjsf.domain.Address;
import id.co.skyforce.basicjsf.domain.Customer;

import java.util.Date;

import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;

import org.hibernate.Session;
import org.hibernate.Transaction;

@ManagedBean
public class CustomerController {
	private String email;
	private String password;
	private String firstName;
	private String lastName;
	private Date birthDate;
	private String mobileNo;
	private String homePhone;
	private String salutation;
	private Character gender;
	
	private String street;
	private String city;
	private String postalCode;
	private Long customerId;
	private Long addressId;

	public Long getAddressId() {
		return addressId;
	}

	public void setAddressId(Long addressId) {
		this.addressId = addressId;
	}

	public CustomerController(){
		String id = FacesContext.getCurrentInstance()
				.getExternalContext()
				.getRequestParameterMap().get("id");
		if(id!=null){
		customerId = Long.valueOf(id);
		Session session = HibernateUtil.openSession();
		Transaction trx = session.beginTransaction();
		
		Customer cus = (Customer) session.get(Customer.class, customerId);
		
		
		addressId = cus.getAddress().getId();
		firstName = cus.getFirstName();
		lastName = cus.getLastName();
		email = cus.getEmail();
		homePhone = cus.getHomePhone();
		mobileNo = cus.getMobileNo();
		password = cus.getPassword();
		birthDate = cus.getBirthDate();
		gender = cus.getGender();
		salutation = cus.getSalutation();
		street = cus.getAddress().getStreet();
		city = cus.getAddress().getCity();
		postalCode = cus.getAddress().getPostalCode();
		
		trx.commit();
		session.close();
		
		}
	}
	
	public void save(){
		Session session = HibernateUtil.openSession();
		Transaction trx = session.beginTransaction();
		Customer cus = new Customer();
		
		cus.setId(customerId);
		
		cus.setFirstName(firstName);
		cus.setLastName(lastName);
		cus.setEmail(email);
		cus.setHomePhone(homePhone);
		cus.setMobileNo(mobileNo);
		cus.setPassword(password);
		cus.setBirthDate(birthDate);
		cus.setGender(gender);
		cus.setSalutation(salutation);
		
		Address ad = new Address();
		ad.setId(addressId);
		ad.setStreet(street);
		ad.setCity(city);
		ad.setPostalCode(postalCode);
		cus.setAddress(ad);
	
		session.saveOrUpdate(cus);
		trx.commit();
		session.close();
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public Date getBirthDate() {
		return birthDate;
	}
	public void setBirthDate(Date birthDate) {
		this.birthDate = birthDate;
	}
	public String getMobileNo() {
		return mobileNo;
	}
	public void setMobileNo(String mobileNo) {
		this.mobileNo = mobileNo;
	}
	public String getHomePhone() {
		return homePhone;
	}
	public void setHomePhone(String homePhone) {
		this.homePhone = homePhone;
	}
	public String getStreet() {
		return street;
	}
	public void setStreet(String street) {
		this.street = street;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getPostalCode() {
		return postalCode;
	}
	public void setPostalCode(String postalCode) {
		this.postalCode = postalCode;
	}

	public String getSalutation() {
		return salutation;
	}

	public void setSalutation(String salutation) {
		this.salutation = salutation;
	}

	public Character getGender() {
		return gender;
	}

	public void setGender(Character gender) {
		this.gender = gender;
	}
	
	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	
}
