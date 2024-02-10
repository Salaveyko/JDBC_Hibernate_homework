package com.jdbc.dao.position;

import com.jdbc.entity.Position;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Data access object that contains CRUD functionality. It is an autocloseable object.
 */
public class PositionJDBC implements PositionDAO {
    private Connection connection;

    public PositionJDBC(Connection connection) {
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
     * Inserts a new record into the 'positions' table.
     *
     * @param position
     * @throws SQLException
     */
    @Override
    public void insert(Position position) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "insert into positions (employee_id, position, salary) " +
                        "values (?, ?, ?)"
        )) {
            statement.setInt(1, position.getEmployeeId());
            statement.setString(2, position.getPosition());
            statement.setInt(3, position.getSalary());
            statement.execute();
        }
    }

    /**
     * Updates a record from the 'positions' table.
     *
     * @param position
     * @throws SQLException
     */
    @Override
    public void update(Position position) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "update positions set position = ?, salary = ? " +
                        "where employee_id = ?"
        )) {
            statement.setString(1, position.getPosition());
            statement.setInt(2, position.getSalary());
            statement.setInt(3, position.getEmployeeId());
            statement.execute();
        }
    }

    /**
     * Deletes a record from the 'positions' table with the specified ID.
     *
     * @param id
     * @throws SQLException
     */
    @Override
    public void delete(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "delete from positions where employee_id = ?"
        )) {
            statement.setInt(1, id);
            statement.execute();
        }
    }

    /**
     * Selects and returns a record from the 'positions' table with the specified ID.
     *
     * @param id
     * @return A Position instance of read data.
     * @throws SQLException
     */
    @Override
    public Position get(int id) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement(
                "select * from positions where employee_id = ?"
        )) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                return new Position(
                        resultSet.getInt("employee_id"),
                        resultSet.getString("position"),
                        resultSet.getInt("salary"));
            }
        }
        return null;
    }
}
