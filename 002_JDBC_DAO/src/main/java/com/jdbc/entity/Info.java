package com.jdbc.entity;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * POJO Info. Contains fields that correspond to the names of the table columns.
 */
public class Info {
    private int employeeId;
    private String address;
    private Date birthdate;
    private boolean married;

    public Info() {
    }

    public Info(int employeeId, String address, Date birthdate, boolean married) {
        this.employeeId = employeeId;
        this.address = address;
        this.birthdate = birthdate;
        this.married = married;
    }

    public int getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(int employeeId) {
        this.employeeId = employeeId;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(Date birthdate) {
        this.birthdate = birthdate;
    }

    public boolean isMarried() {
        return married;
    }

    public void setMarried(boolean married) {
        this.married = married;
    }

    @Override
    public String toString() {
        DateFormat df = new SimpleDateFormat("yyyy.MM.dd");
        return address
                + "\t" + df.format(birthdate)
                + "\t" + (married ? "married" : "not married");
    }
}
