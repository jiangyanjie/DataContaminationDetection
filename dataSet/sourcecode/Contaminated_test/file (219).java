package com.shadowbring.dal;

import com.shadowbring.dal.entity.Redirect;
import com.shadowbring.dal.entity.ServerRequest;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class Dao {

    public static final int NUMBER_OF_REQUESTS_OUTPUT = 100;
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        } catch (Throwable ex) {
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        sessionFactory.close();
    }

    public void mergeServerRequest(ServerRequest serverRequest) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ServerRequest where srcIp = :srcIp")
                .setParameter("srcIp", serverRequest.getSrcIp());
        if (query.list().isEmpty()) {
            session.save(serverRequest);
        } else {
            ServerRequest existingRequest = (ServerRequest) query.uniqueResult();
            existingRequest.setRequestCount(existingRequest.getRequestCount() + 1);
            session.update(existingRequest);
        }
        session.getTransaction().commit();
        session.close();
    }

    public void mergeRedirect(Redirect redirect) {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Redirect where redirectTo = :redirectTo")
                .setParameter("redirectTo", redirect.getRedirectTo());
        if (query.list().isEmpty()) {
            session.save(redirect);
        } else {
            Redirect existingRedirect = (Redirect) query.uniqueResult();
            existingRedirect.setRedirectCount(existingRedirect.getRedirectCount() + 1);
            session.update(existingRedirect);
        }
        session.getTransaction().commit();
        session.close();
    }

    public List<ServerRequest> getServerRequest() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ServerRequest ");
        List<ServerRequest> requestList = query.list();
        session.getTransaction().commit();
        session.close();
        return requestList;
    }

    public List<Redirect> getRedirectRequest() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from Redirect ");
        List<Redirect> redirectList = query.list();
        session.getTransaction().commit();
        session.close();
        return redirectList;
    }

    public char[] getServerRequestCount() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("from ServerRequest");
        Long serverRequestCount = 0L;
        List<Object> requestList = query.list();
        session.getTransaction().commit();
        session.close();
        for (Object serverRequest : requestList) {
            serverRequestCount += ((ServerRequest) serverRequest).getRequestCount();
        }
        return serverRequestCount.toString().toCharArray();
    }

    public char[] getUniqueServerRequestCount() {
        Session session = sessionFactory.openSession();
        session.beginTransaction();
        Query query = session.createQuery("select count (distinct srcIp) from ServerRequest");
        char[] uniqueServerRequestCount = query.uniqueResult().toString().toCharArray();
        session.getTransaction().commit();
        session.close();
        return uniqueServerRequestCount;
    }
}

