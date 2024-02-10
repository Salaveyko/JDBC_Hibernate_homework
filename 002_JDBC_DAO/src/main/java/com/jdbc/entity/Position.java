package com.jdbc.entity;

/**
 * Contains fields that correspond to the names of the table columns.
 */
public class Position {
    private int employeeId;
    private String position;
    private int salary;

    public Position() {
    }

    public Position(int employeeId, String position, int salary) {
        this.employeeId = employeeId;
        this.position = position;
        this.salary = salary;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getSalary() {
        return salary;
    }

    public void setSalary(int salary) {
        this.salary = salary;
    }

    @Override
    public String toString() {
        return position + "\t" + salary;
    }
}
