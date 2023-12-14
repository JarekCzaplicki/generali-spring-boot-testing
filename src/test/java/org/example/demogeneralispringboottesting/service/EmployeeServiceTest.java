package org.example.demogeneralispringboottesting.service;

import org.example.demogeneralispringboottesting.exception.EmployeeAlreadyExists;
import org.example.demogeneralispringboottesting.model.Employee;
import org.example.demogeneralispringboottesting.repository.EmployeeRepository;
import org.example.demogeneralispringboottesting.service.impl.EmployeeServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class) // dodawać tylko, gdy korzystamy z adnotacji
class EmployeeServiceTest {
    // test dla saveEmployee(Employee employee)), która zwraca wyjątek
    // test dla getAllEmployees() - pozytywny scenariusz
    // test dla getAllEmployees() - negatywny scenariusz
    // test dla getEmployeeById(Long id))
    // test dla updateEmployee(Employee updatedEmployee);
    // test dla deleteEmployee(long id);
    @Mock
    private EmployeeRepository employeeRepository;
    @InjectMocks
    private EmployeeServiceImpl employeeService;
//    private EmployeeRepository employeeRepository = mock(EmployeeRepository.class);
//    private EmployeeServiceImpl employeeService = new EmployeeServiceImpl(employeeRepository);

    private Employee employee;

    @BeforeEach
    public void setup() {
        employee = Employee.builder()
                .id(1L)
                .firstName("Jan")
                .lastName("Matejko")
                .email("jmatejko@gmail.com")
                .build();
    }

    @DisplayName("test dla saveEmployee(Employee employee)),bez wyjątku")
    @Test
    void givenEmployee_WhenSaveEmployee_thenReturnEmployee() {
        // given
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Employee savedEmployee = employeeService.saveEmployee(employee);

        // then
        assertThat(savedEmployee).isNotNull();
    }

    //    test dla saveEmployee(Employee employee)), która zwraca wyjątek
    @DisplayName("test dla saveEmployee(Employee employee)), która zwraca wyjątek")
    @Test
    void z() {
        // given
        given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.of(employee));

        // when
        // then
        assertThrows(EmployeeAlreadyExists.class, () -> employeeService.saveEmployee(employee));
        verify(employeeRepository, never()).save(any(Employee.class));
    }
}