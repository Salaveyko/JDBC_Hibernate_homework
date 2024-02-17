package com.hibernate.factory;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton class responsible for providing a Hibernate SessionFactory instance.
 */
public class MyDBFactory {
    private static SessionFactory sessionFactory;

    private MyDBFactory(){}

    /**
     * Retrieves the Hibernate SessionFactory instance.
     * If the instance is not yet created, it builds it using the Hibernate configuration file.
     * @return The Hibernate SessionFactory instance.
     */
    public static SessionFactory getSessionFactory(){
        if (sessionFactory == null) {
            sessionFactory = new Configuration().configure().buildSessionFactory();
        }
        return sessionFactory;
    }
}
