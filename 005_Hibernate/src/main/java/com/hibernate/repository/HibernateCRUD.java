package com.hibernate.repository;

import com.hibernate.entity.Author;
import com.hibernate.entity.MyEntity;

import java.util.List;

public interface HibernateCRUD<T extends MyEntity> {
    T get(long id);

    List<T> getAll();

    void saveOrUpdate(T author);

    void remove(T author);
}
