package com.jdbc.entity;

import java.util.Date;

/**
 * Employee entity. Contains fields that correspond to the names of the table columns, entity fields
 * of other tables that refer to this table, constructors, getters, setters, and the overridden toString() method.
 */
public class Employee {
    private int id;
    private String name;
    private String phone;
    private Info info;
    private Position position;

    public Employee() {
        info = new Info();
        position = new Position();
    }

    public Employee(int id, String name, String phone,
                    String address, Date birthdate, boolean married,
                    String position, int salary) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.info = new Info(id, address, birthdate, married);
        this.position = new Position(id, position, salary);
    }

    public int getId() {
        return id;
    }

    /**
     * Also sets the ID for the Info and Position entities.
     * @param id Employee ID.
     */
    public void setId(int id) {
        this.id = id;
        info.setEmployeeId(id);
        position.setEmployeeId(id);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Info getInfo() {
        return info;
    }

    public void setInfo(Info info) {
        this.info = info;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public String toString() {

        return id + "\t" + name + "\t" + phone + "\t\t" + info + "\t\t" + position;
    }
}
