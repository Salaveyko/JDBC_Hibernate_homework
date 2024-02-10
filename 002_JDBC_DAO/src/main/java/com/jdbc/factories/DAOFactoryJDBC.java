package com.jdbc.factories;

import com.jdbc.dao.employee.EmployeeDAO;
import com.jdbc.dao.employee.EmployeeJDBC;
import com.jdbc.dao.info.InfoDAO;
import com.jdbc.dao.info.InfoJDBC;
import com.jdbc.dao.position.PositionDAO;
import com.jdbc.dao.position.PositionJDBC;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * DAOFactoryJDBC creates a connection to the database and give us some guaranties that there will be no more
 * than one instance of the connection.
 */
public class DAOFactoryJDBC implements DAOFactory{
    private static final String URL = "jdbc:mysql://localhost:3306/my_db";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    private static DAOFactory factory;
    private static Connection connection;

    /**
     * A private constructor that instantiates a connection to a database using a JDBC driver.
     */
    private DAOFactoryJDBC() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("INFO: Successful driver loading.");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return DAOFactoryJDBC instance.
     */
    public static synchronized DAOFactory getInstance() {
        try {
            if (factory == null || connection.isClosed()) {
                factory = new DAOFactoryJDBC();
            }
        } catch (SQLException e) {
            System.err.println("FAILED: " + e.getMessage());
        }
        return factory;
    }

    /**
     * @return A new instance of EmployeeDAO.
     */
    @Override
    public EmployeeDAO getEmployeeDAO () {
        return new EmployeeJDBC(connection);
    }
    /**
     * @return A new instance of InfoDAO.
     */
    @Override
    public InfoDAO getInfoDAO() {
        return new InfoJDBC(connection);
    }
    /**
     * @return A new instance of PositionDAO.
     */
    @Override
    public PositionDAO getPositionDAO() {
        return new PositionJDBC(connection);
    }
}
