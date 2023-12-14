package org.example.demogeneralispringboottesting.service.impl;

import org.example.demogeneralispringboottesting.exception.EmployeeAlreadyExists;
import org.example.demogeneralispringboottesting.model.Employee;
import org.example.demogeneralispringboottesting.repository.EmployeeRepository;
import org.example.demogeneralispringboottesting.service.EmployeeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee saveEmployee(Employee employee) {
        Optional<Employee> savedEmployee = employeeRepository.findByEmail(employee.getEmail());
        if (savedEmployee.isPresent())
            throw new EmployeeAlreadyExists("Employee already exist with given email: " + employee.getEmail());

        return employeeRepository.save(employee);
    }

    @Override
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(Long id) {
        return employeeRepository.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee updatedEmployee) {
        return employeeRepository.save(updatedEmployee);
    }

    @Override
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }
}
