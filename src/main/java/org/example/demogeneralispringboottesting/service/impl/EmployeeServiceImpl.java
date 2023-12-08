package org.example.demogeneralispringboottesting.service.impl;

import org.example.demogeneralispringboottesting.model.Employee;
import org.example.demogeneralispringboottesting.service.EmployeeService;

import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {
    @Override
    public Employee saveEmployee(Employee employee) {
        return null;
    }

    @Override
    public List<Employee> getAllEmployees() {
        return null;
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return Optional.empty();
    }

    @Override
    public void deleteEmployee(long id) {

    }
}
