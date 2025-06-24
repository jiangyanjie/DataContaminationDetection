package DAO.Impl;

import DAO.Abstract.DAO;
import logic.baseDomainObject;
import org.hibernate.Session;
import org.hibernate.metamodel.domain.Superclass;
import util.HibernateUtil;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Alex on 2/10/14.
 */
abstract class DAOHibernate<T extends baseDomainObject> implements DAO<T> {
    public void add(T t) throws SQLException{
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.save(t);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка данных", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public void update(T t) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.update(t);
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка данных", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

    public T getById(int id) throws SQLException {
        Session session = null;
        T t = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            t = (T) session.load(this.getRealClass(), id);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Data error", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return t;
    }

    public List<T> getAll() throws SQLException {
        Session session = null;
        List<T> t = new ArrayList<T>();
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            t = session.createCriteria(this.getRealClass()).list();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка данных I/O", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
        return t;
    }

    public void remove(int id) throws SQLException {
        Session session = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();
            session.delete(this.getById(id));
            session.getTransaction().commit();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Ошибка данных", JOptionPane.OK_OPTION);
        } finally {
            if (session != null && session.isOpen()) {
                session.close();
            }
        }
    }

protected abstract Class getRealClass();

}
