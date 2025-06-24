package com.patent.db;

import java.util.List;
import java.util.UUID;

import org.hibernate.Session;
import org.hibernate.SessionFactory;


public class CustomerDaoImpl implements AbstractCustomerDao{

	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		return sessionFactory;
	}

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public String CreateCustomer(CustomerDaoBean newCustomer) throws Exception {
		String customerId = generateCustomerId();
		newCustomer.setCustomerId(customerId);
		Session session = null;
		try{
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(newCustomer);
			session.getTransaction().commit();

		}finally{
			if(session != null){
				session.close();
			}
		}
		return newCustomer.getCustomerId();
	}

	private String generateCustomerId() {
		UUID uniqueKey = UUID.randomUUID();
		return uniqueKey.toString();
	}

	public String DeleteCustomer(String id) throws Exception {
		Session session = null;
		Integer numRowsDeleted=0;
		try {
			session = sessionFactory.openSession();
			session.beginTransaction();
			numRowsDeleted = session.createQuery(
					"delete from CustomerDaoBean customerDaoBean where customerDaoBean.customerId = ?")
					.setString(0, id)
					.executeUpdate();
			session.getTransaction().commit();

		} finally {
			if (session != null) {
				session.close();
			}
		}
		return numRowsDeleted.toString();
	}

	public CustomerDaoBean GetCustomer(String id) throws Exception {
		Session session = null;
		try {
			session = sessionFactory.openSession();
			return (CustomerDaoBean) session.get(CustomerDaoBean.class,id);
		} finally {
			if (session != null) {
				session.close();
			}
		}
	}

	public String UpdateCustomer(CustomerDaoBean updatedCustomer) throws Exception {
		Session session = null;
		try{
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.saveOrUpdate(updatedCustomer);
			session.getTransaction().commit();
		}finally{
			if(session != null){
				session.close();
			}
		}
		return updatedCustomer.getCustomerId();
	}

	public List<CustomerDaoBean> ListOfCustomer() throws Exception {
		Session session = null;
		List<CustomerDaoBean> listOfCustomer = null;
		try{
			session = sessionFactory.openSession();
			listOfCustomer = (List<CustomerDaoBean>)session.createQuery("FROM CustomerDaoBean").list();
		} finally{
			if(session != null){
				session.close();
			}
		}
		return listOfCustomer;
	}
}
