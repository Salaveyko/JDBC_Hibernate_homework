package com.hibernate;

import com.hibernate.entity.Animal;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class Main {
    public static void main(String[] args) {
        //Creating a new animal instance
        Animal animal = new Animal();
        animal.setId(1);
        animal.setName("Boriska");
        animal.setAge(5);
        animal.setTail(true);

        //Setting up a SessionFactory instance
        SessionFactory factory = new Configuration()
                .configure()
                .buildSessionFactory();
        //Get a session, start transaction, save an object, commit, and close the session.
        Session session = factory.openSession();
        session.beginTransaction();
        session.save(animal);
        session.getTransaction().commit();
        session.close();
    }
}
