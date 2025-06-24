package com.smart.dao.daoSingleton;

import com.smart.dao.AirlineDaoInterface;
import com.smart.hib.config.HiberConfig;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

/**
 * Created by User on 10.11.14.
 */
public class DaoSingleton {
    private static volatile DaoSingleton daoSingletonInstance=null;

    public DaoSingleton() {
    }

    public static DaoSingleton getDaoSingleton() {
        if (daoSingletonInstance == null) {
            synchronized (DaoSingleton.class) {
                if (daoSingletonInstance == null)
                    daoSingletonInstance = new DaoSingleton();
            }
        }
        return daoSingletonInstance;
    }






    public Long addDaoObj(AirlineDaoInterface airlineDaoInterface) {

        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        Long result = -1l;
        try {
            sessionFactory = HiberConfig.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            result = (Long) session.save(airlineDaoInterface);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (!sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        }
        return result;
    }

    public void updateDaoObj(AirlineDaoInterface airlineDaoInterface) {
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;

        try {
            sessionFactory = HiberConfig.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.update(airlineDaoInterface);
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (!sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        }

    }

    public List<AirlineDaoInterface> getAllDaoObj(String tableName) {
         SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;
        List<AirlineDaoInterface> result = null;
        try {
            sessionFactory = HiberConfig.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            result = session.createQuery("from "+tableName).list();
            transaction.commit();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            assert sessionFactory != null;
            if (!sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        }
        return result;

    }
    public void removeDaoObj(AirlineDaoInterface airlineDaoInterface) {
        SessionFactory sessionFactory = null;
        Session session = null;
        Transaction transaction = null;

        try {
            sessionFactory = HiberConfig.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            transaction = session.beginTransaction();
            session.delete(airlineDaoInterface);
            transaction.commit();

        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            if (!sessionFactory.isClosed()) {
                sessionFactory.close();
            }
        }

    }

}
