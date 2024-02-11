package com.hibernate.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * POJO Animal is an entity that represents a table in the database.
 */
@Entity
public class Animal {
    @Id
    private int id;
    private String name;
    private int age;
    private boolean tail;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public boolean isTail() {
        return tail;
    }

    public void setTail(boolean tail) {
        this.tail = tail;
    }
}
