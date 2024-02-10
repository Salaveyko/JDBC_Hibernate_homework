package com.jdbc.dao.employee;

import com.jdbc.entity.Employee;
import com.jdbc.factories.DAOFactory;
import com.jdbc.factories.DAOFactoryJDBC;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Data access object that contains CRUD functionality. It is an autocloseable object.
 */
public class EmployeeJDBC implements EmployeeDAO {
    private Connection connection;
    private static final DAOFactory daoFactory = DAOFactoryJDBC.getInstance();

    public EmployeeJDBC(Connection connection) {
        this.connection = connection;
    }

    /**
     * Creates a new record in the 'employee' table and in related tables that refer to this.
     * Transaction is used to preserve atomicity.
     *
     * @param employee
     * @return ID if the record is inserted successfully, otherwise -1.
     * @throws SQLException
     */
    @Override
    public int insert(Employee employee) throws SQLException {
        int id = -1;

        try (PreparedStatement statement = connection.prepareStatement(
                "insert into employees (name, phone) values (?, ?)",
                Statement.RETURN_GENERATED_KEYS
        )) {
            connection.setAutoCommit(false);

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPhone());
            statement.execute();

            ResultSet rs = statement.getGeneratedKeys();
            if (rs.next()) {
                id = rs.getInt(1);

                employee.getInfo().setEmployeeId(id);
                daoFactory.getInfoDAO().insert(employee.getInfo());

                employee.getPosition().setEmployeeId(id);
                daoFactory.getPositionDAO().insert(employee.getPosition());
            }

            connection.commit();

        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }

        return id;
    }

    /**
     * Selects all records from the 'employees' table and in related tables with a single query.
     *
     * @return The employee list.
     * @throws SQLException
     */
    @Override
    public List<Employee> getAll() throws SQLException {
        List<Employee> employees = new ArrayList<>();

        try (PreparedStatement statement = connection.prepareStatement(
                "select id, name, phone, address, birthdate, married, position, salary" +
                        " from employees" +
                        " inner join positions on employee_id = id" +
                        " inner join info on info.employee_id = id"
        )) {

            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                employees.add(new Employee(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDate("birthdate"),
                        rs.getBoolean("married"),
                        rs.getString("position"),
                        rs.getInt("salary")
                ));
            }

        }

        return employees;
    }

    /**
     * Selects a record from the 'employees' table and in related tables.
     *
     * @param id
     * @return The Employee object.
     * @throws SQLException
     */
    @Override
    public Employee get(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select id, name, phone from employees " +
                        "where id = ?"
        )) {
            statement.setInt(1, id);
            ResultSet rs = statement.executeQuery();
            if (rs.next()) {
                Employee employee = new Employee();

                employee.setId(rs.getInt("id"));
                employee.setName(rs.getString("name"));
                employee.setPhone(rs.getString("phone"));

                employee.setInfo(
                        daoFactory.getInfoDAO().get(id));
                employee.setPosition(
                        daoFactory.getPositionDAO().get(id));

                return employee;
            }
        }

        return null;
    }

    /**
     * Updates all records in the related tables to the Employee entity.
     * The transaction is used to maintain atomicity.
     *
     * @param employee
     * @throws SQLException
     */
    @Override
    public void updateFullEmployee(Employee employee) throws SQLException {
        try {
            connection.setAutoCommit(false);

            updateEmployee(employee);
            updatePosition(employee);
            updateInfo(employee);

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }

    /**
     * Updates a record in the 'employees' table.
     *
     * @param employee
     * @throws SQLException
     */
    @Override
    public void updateEmployee(Employee employee) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update employees set name = ?, phone = ? " +
                        "where id = ?"
        )) {
            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPhone());
            statement.setInt(3, employee.getId());
            statement.execute();
        }
    }

    /**
     * Updates a record in the 'info' table.
     *
     * @param employee
     * @throws SQLException
     */
    @Override
    public void updateInfo(Employee employee) throws SQLException {
        daoFactory.getInfoDAO().update(employee.getInfo());
    }

    /**
     * Updates a record in the 'positions' table.
     *
     * @param employee
     * @throws SQLException
     */
    @Override
    public void updatePosition(Employee employee) throws SQLException {
        daoFactory.getPositionDAO().update(employee.getPosition());
    }

    /**
     * Deletes all records in the related tables to the Employee entity.
     * The transaction is used to maintain atomicity.
     *
     * @param id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from employees where id = ?"
        )) {
            connection.setAutoCommit(false);

            daoFactory.getInfoDAO().delete(id);
            daoFactory.getPositionDAO().delete(id);

            statement.setInt(1, id);
            statement.execute();

            connection.commit();
        } catch (SQLException e) {
            connection.rollback();
            throw e;
        } finally {
            connection.setAutoCommit(true);
        }
    }


    /**
     * Overridden method of the AutoCloseable interface. Closes the connection to a database.
     *
     * @throws SQLException
     */
    @Override
    public void close() throws SQLException {
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                throw e;
            }
            connection.close();
        }
    }
}
