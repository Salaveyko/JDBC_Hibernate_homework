package com.jpa.persistence;

import com.jpa.entity.Book;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class BookHelper {
    private EntityManagerFactory factory;

    public BookHelper() {
        factory = Persistence.createEntityManagerFactory("BookPU");
    }

    public void close() {
        factory.close();
    }

    /**
     * Add a book to the database.
     *
     * @param book The book to add.
     */
    public void add(Book book) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction tx = manager.getTransaction();

        try {
            tx.begin();
            manager.persist(book);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    /**
     * Update a book in the database.
     *
     * @param book The book to update.
     */
    public void update(Book book) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction tx = manager.getTransaction();

        try {
            tx.begin();
            book = manager.merge(book);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    /**
     * Remove a book from the database by its ID.
     *
     * @param id The ID of the book to remove.
     */
    public void remove(long id) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction tx = manager.getTransaction();

        try {
            tx.begin();

            Book toDel = manager.find(Book.class, id);
            if (toDel != null) {
                manager.remove(toDel);
            }

            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }
    }

    /**
     * Retrieve a book from the database by its ID.
     *
     * @param id The ID of the book to retrieve.
     * @return The book object if found, otherwise null.
     */
    public Book get(long id) {
        EntityManager manager = factory.createEntityManager();
        EntityTransaction tx = manager.getTransaction();
        Book book = null;

        try {
            tx.begin();
            book = manager.find(Book.class, id);
            tx.commit();
        } catch (Exception e) {
            if (tx.isActive()) tx.rollback();
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return book;
    }

    /**
     * Retrieve all books from the database.
     *
     * @return A list of all books in the database.
     */
    public List<Book> getBooks() {
        EntityManager manager = factory.createEntityManager();

        try {
            CriteriaBuilder criteriaBuilder = manager.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = criteriaQuery.from(Book.class);
            criteriaQuery.select(root);

            TypedQuery<Book> query = manager.createQuery(criteriaQuery);
            return query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            manager.close();
        }

        return new ArrayList<>();
    }
}
