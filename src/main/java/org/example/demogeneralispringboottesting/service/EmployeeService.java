package org.example.demogeneralispringboottesting.service;

import org.example.demogeneralispringboottesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    Employee saveEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(Long id);

    void deleteEmployee(long id);
}
