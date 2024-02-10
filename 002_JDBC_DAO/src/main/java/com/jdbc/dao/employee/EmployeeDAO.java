package com.jdbc.dao.employee;

import com.jdbc.entity.Employee;

import java.sql.SQLException;
import java.util.List;

public interface EmployeeDAO extends AutoCloseable {
    int insert(Employee employee) throws SQLException;

    List<Employee> getAll() throws SQLException;

    Employee get(int id) throws SQLException;

    void updateFullEmployee(Employee employee) throws SQLException;

    void updateEmployee(Employee employee) throws SQLException;

    void updateInfo(Employee employee) throws SQLException;

    void updatePosition(Employee employee) throws SQLException;

    void delete(int id) throws SQLException;

    @Override
    void close() throws SQLException;
}
