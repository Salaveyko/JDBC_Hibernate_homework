package com.hibernate.factories;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Singleton class responsible for providing a Hibernate SessionFactory instance.
 */
public class MyDBSessionFactory {
    // Static reference to the Hibernate SessionFactory instance.
    private static SessionFactory factory;

    // Private constructor to prevent instantiation.
    private MyDBSessionFactory(){}

    /**
     * Retrieves the Hibernate SessionFactory instance.
     * If the instance is not yet created, it builds it using the Hibernate configuration file.
     * @return The Hibernate SessionFactory instance.
     */
    public static SessionFactory getSessionFactory(){
        if(factory == null) {
            factory = new Configuration().configure().buildSessionFactory();
        }
        return factory;
    }
}
