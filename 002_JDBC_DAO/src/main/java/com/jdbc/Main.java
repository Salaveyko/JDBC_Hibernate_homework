package com.jdbc;

import com.jdbc.dao.employee.EmployeeDAO;
import com.jdbc.entity.Employee;
import com.jdbc.factories.DAOFactory;
import com.jdbc.factories.DAOFactoryJDBC;

import java.sql.SQLException;
import java.util.*;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        Employee employee = new Employee(
                0,
                "Naomi",
                "123123123",
                "New York",
                new GregorianCalendar(1993, Calendar.MARCH, 13).getTime(),
                false,
                "Manager",
                50000
        );

        try(EmployeeDAO employeeDAO = DAOFactoryJDBC.getInstance().getEmployeeDAO()) {

            System.out.println("\nINFO: Selecting the list of employees");
            printEmployees(employeeDAO.getAll());

            System.out.println("INFO: Inserting a new manager");
            Employee employeeTmp = employee;

            int id = 0;
            try {
                id = employeeDAO.insert(employeeTmp);
                System.out.println("INFO: A new manager has been inserted. ID - " + id);
            } catch (SQLException e) {
                System.err.println("FAILED: " + e.getMessage());
            }

            System.out.print("\nEnter the employee ID to select: ");
            id = scan.nextInt();
            employeeTmp = employeeDAO.get(id);
            System.out.println("INFO: The employee has been selected");
            System.out.println(employeeTmp);

            System.out.print("\nEnter a new name for the selected employee: ");
            employeeTmp.setName(scan.next());
            employeeDAO.updateEmployee(employeeTmp);
            System.out.println("INFO: The 'employees' table has been updated");

            System.out.print("\nEnter a new address for the selected employee: ");
            employeeTmp.getInfo().setAddress(scan.next());
            employeeDAO.updateInfo(employeeTmp);
            System.out.println("INFO: The 'info' table has been updated");

            System.out.print("\nEnter a new salary for the selected employee: ");
            employeeTmp.getPosition().setSalary(scan.nextInt());
            employeeDAO.updatePosition(employeeTmp);
            System.out.println("INFO: The 'positions' table has been updated");
            System.out.println(employeeDAO.get(id));

            System.out.println("\nJust testing the full update of employee data");
            employeeTmp = employee;
            employeeTmp.setId(id);
            employeeDAO.updateFullEmployee(employeeTmp);
            System.out.println("INFO: Employee data is fully updated");
            System.out.println(employeeDAO.get(id));

            System.out.println("\nDelete a selected employee");
            employeeDAO.delete(id);
            System.out.println("INFO: Employee has been successfully deleted");

            System.out.println("\nINFO: Final selection");
            printEmployees(employeeDAO.getAll());
        } catch (SQLException e) {
            System.err.println("FAILED: " + e.getMessage());
        }
    }
    private static void printEmployees(List<Employee> employees){
        for(Employee e : employees){
            System.out.println(e);
        }

        System.out.println();
    }
}
