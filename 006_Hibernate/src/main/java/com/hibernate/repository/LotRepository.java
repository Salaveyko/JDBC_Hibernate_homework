package com.hibernate.repository;

import com.hibernate.entity.Car;
import com.hibernate.entity.Lot;
import com.hibernate.factory.MyDBFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.*;
import java.util.List;

/**
 * Class responsible for retrieving data from the database using Hibernate.
 */
public class LotRepository {
    private final SessionFactory factory;

    public LotRepository() {
        factory = MyDBFactory.getSessionFactory();
    }

    /**
     * Retrieves all Lot entities.
     *
     * @return List of Lot entities.
     */
    public List<Lot> getAll() {
        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Lot> cq = cb.createQuery(Lot.class);
            cq.from(Lot.class);

            return session.createQuery(cq).getResultList();
        }
    }

    /**
     * Retrieves a Lot entity by its ID.
     *
     * @param id ID of the Lot.
     * @return Lot entity if found, otherwise null.
     */
    public Lot getById(Long id) {
        try (Session session = factory.openSession()) {
            return session.get(Lot.class, id);
        }
    }

    /**
     * Retrieves lots by the brand of the car associated with them.
     *
     * @param brand The brand of the car to filter the lots by
     * @return A list of lots associated with cars of the specified brand
     */
    public List<Lot> getByCarBrand(String brand) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Lot> cr = cb.createQuery(Lot.class);

            // getting all records from the 'lots' table
            Root<Lot> root = cr.from(Lot.class);
            // joining the 'car' table
            Join<Lot, Car> carJoin = root.join("car");
            // selecting of records where the predicate equal true
            cr.where(cb.equal(carJoin.get("brand"), brand));

            return session.createQuery(cr).getResultList();
        }
    }

    /**
     * Retrieves lots from the price range.
     *
     * @param min The minimum price.
     * @param max The maximum price.
     * @return A list of lots related to cars in the specified price range.
     */
    public List<Lot> getByPriceBetween(double min, double max) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Lot> cr = cb.createQuery(Lot.class);

            Root<Lot> root = cr.from(Lot.class);
            cr.where(cb.between(root.get("price"), min, max));

            return session.createQuery(cr).getResultList();
        }
    }

    /**
     * Retrieves lots by the brand of the car and price range.
     *
     * @param brand The brand of the car to filter the lots by.
     * @param min   The minimum price.
     * @param max   The maximum price.
     * @return A list of lots associated with cars of the specified brand and within the price range.
     */
    public List<Lot> getByBrandAndPriceBetween(String brand, double min, double max) {
        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Lot> cr = cb.createQuery(Lot.class);

            Root<Lot> root = cr.from(Lot.class);
            Join<Lot, Car> carJoin = root.join("car");

            Predicate between = cb.between(root.get("price"), min, max);
            Predicate equal = cb.equal(carJoin.get("brand"), brand);
            cr.where(cb.and(between, equal));

            return session.createQuery(cr).getResultList();
        }
    }

    /**
     * Deletes lots by their IDs.
     *
     * @param ids The list of IDs of lots to delete
     */
    public void deleteByIdsList(List<Long> ids) {
        try (Session session = factory.openSession()) {
            session.beginTransaction();

            for (var id : ids) {
                session.delete(
                        session.get(Lot.class, id));
            }
            session.getTransaction().commit();
        }
    }

    /**
     * Saves the given list of lots into the database.
     *
     * @param lots The list of lots to be saved.
     */
    public void saveLots(List<Lot> lots) {
        if (!lots.isEmpty()) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                for (var l : lots) {
                    session.persist(l);
                }

                session.getTransaction().commit();
            }
        }
    }

    /**
     * Updates the given list of lots in the database.
     *
     * @param lots The list of lots to be updated.
     */
    public void updateLots(List<Lot> lots) {
        if (!lots.isEmpty()) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                for (var c : lots) {
                    session.merge(c);
                }

                session.getTransaction().commit();
            }
        }
    }
}
