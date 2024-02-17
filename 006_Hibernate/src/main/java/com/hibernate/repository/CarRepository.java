package com.hibernate.repository;

import com.hibernate.entity.Car;
import com.hibernate.entity.Lot;
import com.hibernate.factory.MyDBFactory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.persistence.criteria.Subquery;
import java.util.List;

public class CarRepository {
    private final SessionFactory factory;

    public CarRepository() {
        factory = MyDBFactory.getSessionFactory();
    }

    /**
     * Finds cars without associated lots.
     *
     * @return A list of cars without associated lots
     */
    public List<Car> findCarsWithoutLots() {
        try (Session session = factory.openSession()) {
            CriteriaBuilder cb = session.getCriteriaBuilder();
            CriteriaQuery<Car> cq = cb.createQuery(Car.class);
            Root<Car> root = cq.from(Car.class);

            Subquery<Long> subQuery = cq.subquery(Long.class);
            Root<Lot> subRoot = subQuery.from(Lot.class);
            subQuery.select(subRoot.get("car").get("id"));
            cq.where(cb.not(root.get("id").in(subQuery)));

            return session.createQuery(cq).getResultList();
        }
    }

    /**
     * Saves the given list of cars into the database.
     *
     * @param cars The list of cars to be saved.
     */
    public void saveCars(List<Car> cars) {
        if (!cars.isEmpty()) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                for (var c : cars) {
                    session.persist(c);
                }

                session.getTransaction().commit();
            }
        }
    }

    /**
     * Deletes the given list of cars from the database.
     *
     * @param cars The list of cars to be deleted.
     */
    public void deleteCars(List<Car> cars) {
        if (!cars.isEmpty()) {
            try (Session session = factory.openSession()) {
                session.beginTransaction();

                for (var c : cars) {
                    session.delete(c);
                }

                session.getTransaction().commit();
            }
        }
    }
}
