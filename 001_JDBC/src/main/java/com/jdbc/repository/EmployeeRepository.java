package com.jdbc.repository;

import com.jdbc.entity.Employee;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class EmployeeRepository {

    private static final String URL = "jdbc:mysql://localhost:3306/my_db";
    private static final String LOGIN = "root";
    private static final String PASSWORD = "root";

    /**
     * The QueryBuilder is a simple constructor that creates a database query.
     */
    private static class QueryBuilder {
        private final StringBuilder query;

        public QueryBuilder() {
            query = new StringBuilder();
        }

        protected String build() {
            return query.toString().trim();
        }

        protected QueryBuilder select(String columns) {
            query.append("SELECT ").append(columns).append(" ");
            return this;
        }

        protected QueryBuilder from(String table) {
            query.append("FROM ").append(table).append(" ");
            return this;
        }

        protected QueryBuilder innerJoin(String table, String onColumns) {
            query.append("INNER JOIN ").append(table).append(" on ").append(onColumns).append(" ");
            return this;
        }

        protected QueryBuilder where(String expression) {
            query.append("WHERE ").append(expression).append(" ");
            return this;
        }

        protected QueryBuilder insert(String table, String columns) {
            query.append("INSERT INTO ").append(table).append(" (").append(columns).append(") ");
            return this;
        }

        protected QueryBuilder values(String values) {
            query.append("VALUES (").append(values).append(") ");
            return this;
        }

        protected QueryBuilder update(String table, String set) {
            query.append("UPDATE ").append(table).append(" set ").append(set).append(" ");
            return this;
        }

        protected QueryBuilder delete() {
            query.append("DELETE ").append(" ");
            return this;
        }
    }

    /**
     * Initializing the MySQL driver.
     */
    public EmployeeRepository() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("INFO: Driver loading success.");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return Map of employees.
     */
    public static Map<Integer, Employee> getAllEmployees() {
        return getResult(selectAllEmployees());
    }

    /**
     * @return Map of employees who are not married.
     */
    public static Map<Integer, Employee> getNonMarried() {
        String query = selectAllEmployees();
        query = query + " " + new QueryBuilder()
                .where("married = false")
                .build();

        return getResult(query);
    }

    /**
     * @return Map of managers.
     */
    public static Map<Integer, Employee> getAllManagers() {
        String query = selectAllEmployees();
        query = query + " " + new QueryBuilder()
                .where("position = 'Manager'")
                .build();

        return getResult(query);
    }

    /**
     * Inserts an employee into the 'employees' table. Creates records in the 'info' and 'positions'
     * tables using a transaction that refers to the inserted record from the 'employees' table.
     *
     * @param employee Employee entity.
     * @return Employee ID if successful, otherwise - 0.
     */
    public static int insertEmployee(Employee employee) {
        int id = 0;

        try (Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            // insert data with 1 transaction
            conn.setAutoCommit(false);

            String query = new QueryBuilder()
                    .insert("employees", "name, phone")
                    .values("?, ?")
                    .build();

            try (PreparedStatement statement = conn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
                statement.setString(1, employee.getName());
                statement.setString(2, employee.getPhone());
                statement.execute();

                ResultSet resSet = statement.getGeneratedKeys();
                if (resSet.next()) {
                    id = resSet.getInt(1);

                    insertInfo(employee, id, conn);
                    insertPosition(employee, id, conn);

                    conn.commit();
                } else {
                    conn.rollback();
                }
            }
        } catch (SQLException e) {
            printError(e);
            id = 0;
        }

        return id;
    }

    /**
     * Updates an employee in the 'employees' table.
     *
     * @param employee Employee entity.
     */
    public static void updateEmployee(Employee employee) {
        String query = new QueryBuilder()
                .update("employees", "name = ?, phone = ?")
                .where("id = ?")
                .build();

        try (Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, employee.getName());
            statement.setString(2, employee.getPhone());
            statement.setInt(3, employee.getId());
            statement.execute();
        } catch (SQLException e) {
            printError(e);
        }
    }

    /**
     * Updates information about the employee in the 'info' table.
     *
     * @param employee Employee entity.
     */
    public static void updateInfo(Employee employee) {
        String query = new QueryBuilder()
                .update("info", "address = ?, birthdate = ?, married = ?")
                .where("employee_id = ?")
                .build();

        try (Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, employee.getAddress());
            statement.setDate(2, new java.sql.Date(employee.getBirthdate().getTime()));
            statement.setBoolean(3, employee.isMarried());
            statement.setInt(4, employee.getId());
            statement.execute();
        } catch (SQLException e) {
            printError(e);
        }
    }

    /**
     * Updates position of the employee in the 'positions' table.
     *
     * @param employee Employee entity.
     */
    public static void updatePosition(Employee employee) {
        String query = new QueryBuilder()
                .update("positions", "position = ?, salary = ?")
                .where("employee_id = ?")
                .build();

        try (Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setString(1, employee.getPosition());
            statement.setInt(2, employee.getSalary());
            statement.setInt(3, employee.getId());
            statement.execute();
        } catch (SQLException e) {
            printError(e);
        }
    }

    /**
     * Deletes en employee by id with all information about using a transaction.
     *
     * @param id Employee id.
     */
    public static void deleteEmployee(int id) {
        try (Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD)) {
            conn.setAutoCommit(false);

            deleteFrom("info", "employee_id", id, conn);
            deleteFrom("positions", "employee_id", id, conn);
            deleteFrom("employees", "id", id, conn);

            conn.commit();
        } catch (SQLException e) {
            printError(e);
        }
    }

    /**
     * Deleting a record from the database. This is the part of the transaction that cause transaction
     * to roll back in case of failure.
     *
     * @param table      Table name.
     * @param primaryKey Name of the primary key column.
     * @param id         Record id.
     * @param conn       Database connection.
     * @throws SQLException
     */
    private static void deleteFrom(String table, String primaryKey, int id, Connection conn) throws SQLException {
        String query = new QueryBuilder()
                .delete()
                .from(table)
                .where(primaryKey + " = ?")
                .build();

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, id);
            statement.execute();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }

    /**
     * Inserting a record into the 'info' table of the database. This is the part of the transaction that cause transaction
     * to roll back in case of failure.
     *
     * @param employee Employee entity
     * @param id       Employee id
     * @param conn     Database connection
     * @throws SQLException
     */
    private static void insertInfo(Employee employee, int id, Connection conn) throws SQLException {
        String query = new QueryBuilder()
                .insert("info", "employee_id, address, birthdate, married")
                .values("?, ?, ?, ?")
                .build();
        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.setString(2, employee.getAddress());
            statement.setDate(3, new java.sql.Date(employee.getBirthdate().getTime()));
            statement.setBoolean(4, employee.isMarried());
            statement.execute();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }

    /**
     * Inserting a record into the 'positions' table of the database. This is the part of the transaction
     * that cause transaction to roll back in case of failure.
     *
     * @param employee Employee entity
     * @param id       Employee id
     * @param conn     Database connection
     * @throws SQLException
     */
    private static void insertPosition(Employee employee, int id, Connection conn) throws SQLException {
        String query = new QueryBuilder()
                .insert("positions", "employee_id, position, salary")
                .values("?, ?, ?")
                .build();

        try (PreparedStatement statement = conn.prepareStatement(query)) {

            statement.setInt(1, id);
            statement.setString(2, employee.getPosition());
            statement.setInt(3, employee.getSalary());
            statement.execute();
        } catch (SQLException e) {
            conn.rollback();
            throw e;
        }
    }

    /**
     * Prints an exception message.
     *
     * @param e Exception.
     */
    private static void printError(Exception e) {
        System.err.println("FAILED: " + e.getMessage());
    }

    private static String selectAllEmployees() {
        return new QueryBuilder()
                .select("id, name, phone, address, birthdate, married, position, salary")
                .from("employees")
                .innerJoin("positions", "employee_id = id")
                .innerJoin("info", "info.employee_id = id")
                .build();
    }

    /**
     * Carries out selection of employees.
     *
     * @param query Selection request.
     * @return Map of employees.
     */
    private static Map<Integer, Employee> getResult(String query) {
        Map<Integer, Employee> employees = new HashMap<>();

        try (Connection conn = DriverManager.getConnection(URL, LOGIN, PASSWORD);
             PreparedStatement statement = conn.prepareStatement(query)) {

            ResultSet res = statement.executeQuery();
            while (res.next()) {
                int id = res.getInt("id");
                employees.put(id, new Employee(
                        id,
                        res.getString("name"),
                        res.getString("phone"),
                        res.getString("address"),
                        res.getDate("birthdate"),
                        res.getBoolean("married"),
                        res.getString("position"),
                        res.getInt("salary"))
                );
            }

        } catch (SQLException e) {
            printError(e);
        }

        return employees;
    }
}
