package com.jdbc;

import com.jdbc.entity.Employee;
import com.jdbc.repository.EmployeeRepository;

import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        //select all employees
        System.out.println("Employees list:");
        printEmployees(EmployeeRepository.getAllEmployees());

        System.out.println("Not married employees:");
        printEmployees(EmployeeRepository.getNonMarried());

        System.out.println("Managers:");
        printEmployees(EmployeeRepository.getAllManagers());

        //Insert new employees to employees table
        int id = EmployeeRepository.insertEmployee(new Employee(
                0, "Mykola", "+380(96)3213223", "Kopernika",
                new GregorianCalendar(1985, 5, 5).getTime(),
                true, "Manager", 45000));
        if (id != 0) System.out.println("New employee id: " + id);

        id = EmployeeRepository.insertEmployee(new Employee(
                0, "Alina", "+380(96)3211113", "Kurchatova",
                new GregorianCalendar(1990, 11, 19).getTime(),
                false, "Manager", 45000));
        if (id != 0) System.out.println("New employee id: " + id);

        printEmployees(EmployeeRepository.getAllEmployees());

        //Delete an employee
        System.out.print("Enter the id of the employee you want to delete: ");
        EmployeeRepository.deleteEmployee(scan.nextInt());

        //Update employee in employees table
        Map<Integer, Employee> employees = EmployeeRepository.getAllEmployees();

        System.out.print("Enter an id of the employee: ");
        Employee employeeToUpdate = employees.get(scan.nextInt());

        System.out.print("Enter a new name: ");
        employeeToUpdate.setName(scan.next());
        System.out.print("Enter a new salary: ");
        employeeToUpdate.setSalary(scan.nextInt());
        System.out.print("Enter a new address: ");
        employeeToUpdate.setAddress(scan.next());

        EmployeeRepository.updateEmployee(employeeToUpdate);
        EmployeeRepository.updatePosition(employeeToUpdate);
        EmployeeRepository.updateInfo(employeeToUpdate);

        printEmployees(EmployeeRepository.getAllEmployees());
    }

    private static void printEmployees(Map<Integer, Employee> employees) {
        System.out.println();
        for (Employee e : employees.values()) {
            System.out.println(e);
        }
        System.out.println();
    }
}
