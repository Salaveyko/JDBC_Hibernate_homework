package com.jdbc.dao.info;

import com.jdbc.entity.Info;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object that contains CRUD functionality. It is an autocloseable object.
 */
public class InfoJDBC implements InfoDAO {
    private Connection connection;

    public InfoJDBC(Connection connection) {
        this.connection = connection;
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
            connection = null;
        }
    }

    /**
     * Inserts a new record into the 'info' table.
     *
     * @param info
     * @throws SQLException
     */
    @Override
    public void insert(Info info) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into info (employee_id, address, birthdate, married) " +
                        "values (?, ?, ?, ?) "
        )) {
            statement.setInt(1, info.getEmployeeId());
            statement.setString(2, info.getAddress());
            statement.setDate(3, new java.sql.Date(info.getBirthdate().getTime()));
            statement.setBoolean(4, info.isMarried());
            statement.execute();
        }
    }

    /**
     * Updates a record from the 'info' table.
     *
     * @param info
     * @throws SQLException
     */
    @Override
    public void update(Info info) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update info set address = ?, birthdate = ?, married = ? " +
                        "where employee_id = ?"
        )) {
            statement.setString(1, info.getAddress());
            statement.setDate(2, new java.sql.Date(info.getBirthdate().getTime()));
            statement.setBoolean(3, info.isMarried());
            statement.setInt(4, info.getEmployeeId());
            statement.execute();
        }
    }

    /**
     * Deletes a record from the 'info' table with the specified ID.
     *
     * @param id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from info where employee_id = ?"
        )) {
            statement.setInt(1, id);
            statement.execute();
        }
    }

    /**
     * Selects and returns a record from the 'positions' table with the specified ID.
     *
     * @param id
     * @return An Info instance of read data.
     * @throws SQLException
     */
    @Override
    public Info get(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from info where employee_id = ?"
        )) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Info(
                        resultSet.getInt("employee_id"),
                        resultSet.getString("address"),
                        resultSet.getDate("birthdate"),
                        resultSet.getBoolean("married"));
            }
        }
        return null;
    }
}
