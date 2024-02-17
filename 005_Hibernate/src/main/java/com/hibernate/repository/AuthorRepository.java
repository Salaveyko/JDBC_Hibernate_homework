package com.hibernate.repository;

import com.hibernate.entity.Author;
import com.hibernate.factories.MyDBSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.List;

/**
 * Class responsible for CRUD operations for Author entities using Hibernate.
 */
public class AuthorRepository implements HibernateCRUD<Author> {
    private final SessionFactory factory;

    /**
     * Constructor initializing the SessionFactory.
     */
    public AuthorRepository() {
        factory = MyDBSessionFactory.getSessionFactory();
    }

    /**
     * Retrieves an Author entity by its ID.
     * @param id ID of the author.
     * @return Author entity if found, otherwise null.
     */
    @Override
    public Author get(long id) {
        try (Session session = factory.openSession()) {
            return session.get(Author.class, id);
        }
    }

    /**
     * Retrieves all Author entities.
     * @return List of Author entities.
     */
    @Override
    public List<Author> getAll() {
        try (Session session = factory.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Author> query = builder.createQuery(Author.class);
            query.from(Author.class);

            return session.createQuery(query).getResultList();
        }
    }

    /**
     * Saves or updates an Author entity in the database.
     * @param author The Author entity to be saved or updated.
     */
    @Override
    public void saveOrUpdate(Author author) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(author);
            session.getTransaction().commit();
        }
    }

    /**
     * Removes an Author entity from the database.
     * @param author The Author entity to be removed.
     */
    @Override
    public void remove(Author author) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(author);
            session.getTransaction().commit();
        }
    }
}
