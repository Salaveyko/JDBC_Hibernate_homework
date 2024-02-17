package com.hibernate.repository;

import com.hibernate.entity.Post;
import com.hibernate.factories.MyDBSessionFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import java.util.Date;
import java.util.List;

/**
 * Class responsible for CRUD operations for Post entities using Hibernate.
 */
public class PostRepository implements HibernateCRUD<Post> {
    private final SessionFactory factory;

    /**
     * Constructor initializing the SessionFactory.
     */
    public PostRepository() {
        factory = MyDBSessionFactory.getSessionFactory();
    }

    /**
     * Retrieves a Post entity by its ID.
     * @param id ID of the post.
     * @return Post entity if found, otherwise null.
     */
    @Override
    public Post get(long id) {
        try (Session session = factory.openSession()) {
            return session.get(Post.class, id);
        }
    }

    /**
     * Retrieves all Post entities.
     * @return List of Post entities.
     */
    @Override
    public List<Post> getAll() {
        try (Session session = factory.openSession()) {

            CriteriaBuilder builder = session.getCriteriaBuilder();
            CriteriaQuery<Post> query = builder.createQuery(Post.class);
            query.from(Post.class);

            return session.createQuery(query).getResultList();
        }
    }

    /**
     * Saves or updates a Post entity in the database.
     * @param post The Post entity to be saved or updated.
     */
    @Override
    public void saveOrUpdate(Post post) {
        post.setUpdated(new Date());
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.saveOrUpdate(post);
            session.getTransaction().commit();
        }
    }

    /**
     * Removes a Post entity from the database.
     * @param post The Post entity to be removed.
     */
    @Override
    public void remove(Post post) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();
            session.delete(post);
            session.getTransaction().commit();
        }
    }
}
