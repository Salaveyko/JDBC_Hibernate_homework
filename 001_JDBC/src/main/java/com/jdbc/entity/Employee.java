package com.jdbc.entity;

import java.util.Date;

/**
 * POJO Employee
 */
public class Employee {
    private int id;
    private String name;
    private String phone;
    private String address;
    private Date birthdate;
    private boolean married;
    private String position;
    private int salary;

    public Employee() {
    }

    public Employee(int id, String name, String phone, String address,
                    Date birthdate, boolean married, String position, int salary) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.birthdate = birthdate;
        this.married = married;
        this.position = position;
        this.salary = salary;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public boolean isMarried() {
        return married;
    }

    public String getPosition() {
        return position;
    }

    public int getSalary() {
        return salary;
    }

    @Override
    public String toString() {
        return id + "\t" + name + "\t" + phone + "\t\t\t" + address + "\t"
                + birthdate + "\t" + (married ? "married" : "not married") + "\t\t\t"
                + position + "\t" + salary;
    }
}
