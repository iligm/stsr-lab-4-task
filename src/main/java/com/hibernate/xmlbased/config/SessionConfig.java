package com.hibernate.xmlbased.config;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import com.hibernate.xmlbased.model.Department;
import com.hibernate.xmlbased.model.Developer;
import com.hibernate.xmlbased.model.User;

public class SessionConfig {

    private static SessionConfig sc;
    final SessionFactory sessionFactory;

    private SessionConfig() {
        sessionFactory = new Configuration()
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Developer.class)
                .addAnnotatedClass(Department.class)
                .configure()
                .buildSessionFactory();
    }

    public SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static SessionConfig getInstanceOfSeccionFactory() {
        if (sc == null)
            sc = new SessionConfig();
        return sc;
    }
}
