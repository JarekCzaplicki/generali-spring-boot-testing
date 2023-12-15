package org.example.demogeneralispringboottesting.repository;

import org.example.demogeneralispringboottesting.model.Employee;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
//@SpringBootTest
@DataJpaTest// domyślnie używa in-memory database, testy są w ramach transakcji i jest robiony 'roll back' na koniec każdego testu
class EmployeeRepositoryTest {
    // - zapisywanie pracowników
// - pobieranie wszystkich pracowników
// - pobieranie pracowników po 'Id'
// - pobieranie po adresie 'Emial'
// - uaktualnienie danych pracowników
// - usuwanie pracowników
// - testy dla zapytań za pomocą JPQL
// - testy dla zapytań z indeksowanymi parametrami
// BDD :
// given - aktualne ustawienie, obiekty itp
// when - akcje lub zachowanie jakie testujemy
// then - weryfikacja otrzymanego rezultatu
    @Autowired
    private EmployeeRepository employeeRepository;

    private Employee employee;
    @BeforeEach
    void setUp(){
        employee = Employee.builder()
                .firstName("Adam")
                .lastName("Małysz")
                .email("adam@gmail.com")
                .build();

    }

    //    @DisplayName("JUnit test dla zapisywania pracowników")
    @Test
    void givenEmployeeObject_whenSave_thenReturnSavedEmployee() {
        // given
        // when
        Employee savedEmployee = employeeRepository.save(employee);

        // then
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    @DisplayName("pobieranie wszystkich pracowników")
    @Test
    void givenEmployeeList_whenFindAll_thenEmployeeList() {
        // given
        Employee employee1 = Employee.builder()
                .firstName("Robert")
                .lastName("Kubica")
                .email("robert@gmail.com")
                .build();
        employeeRepository.save(employee);
        employeeRepository.save(employee1);

        // when
        List<Employee> employeeList = employeeRepository.findAll();

        // then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }


    @DisplayName("pobieranie pracowników po 'Id'")
    @Test
    void givenEmployeeObject_whenFindById_thenReturnedEmployeeObject() {
        // given
        employeeRepository.save(employee);

        // when
        Employee employeeDB = employeeRepository.findById(employee.getId()).get();

        // then
        assertThat(employeeDB).isNotNull();
    }
// - pobieranie po adresie 'Emial'

    @DisplayName("pobieranie po adresie 'Emial'")
    @Test
    void givenEmployeeObject_whenFindByEmail_thenReturnEmployeeObject() {
        // given
        employeeRepository.save(employee);

        // when
        Employee employeeDB = employeeRepository.findByEmail(employee.getEmail()).get();

        // then
        assertThat(employeeDB).isNotNull();
    }

    @DisplayName("uaktualnienie danych pracowników")
    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnUpdatedEmployee() {
        // given - precondition or setup
        employeeRepository.save(employee);

        // when
        Employee savedEmployee = employeeRepository.findById(employee.getId()).get();
        savedEmployee.setEmail("jarek@gmail.com");
        savedEmployee.setFirstName("Jarek");
        Employee updatedEmployee = employeeRepository.save(savedEmployee);

        // then
        assertThat(updatedEmployee.getEmail()).isEqualTo("jarek@gmail.com");
        assertThat(updatedEmployee.getFirstName()).isEqualTo("Jarek");
    }

    @DisplayName("Usuwanie pracowników")
    @Test
    void givenEmployee_whenDelete_thenEmployeeNotPresent() {
        // given
        employeeRepository.save(employee);

        // when
        employeeRepository.deleteById(employee.getId());
        Optional<Employee> employeeOptional = employeeRepository.findById(employee.getId());

        // then
        assertThat(employeeOptional).isEmpty();
    }

    @DisplayName("testy dla zapytań za pomocą JPQL z bindowaniem parametrów")
    @Test
    void givenFirstNameAndLastName_whenFindByJPQL_thenReturnedEmployee() {
        // given
        employeeRepository.save(employee);
        String firstName = "Adam";
        String lastName = "Małysz";

        // when
        Employee findedEmployee = employeeRepository.findByJPQL(firstName, lastName);

        // then
        assertThat(findedEmployee).isNotNull();
        assertThat(findedEmployee).isEqualTo(employee);
    }

    @DisplayName("testy dla zapytań za pomocą JPQL z nazwami parametrów")
    @Test
    void givenFirstNameAndLastName_whenFindByJPQLNamedParams_thenReturnedEmployee() {
        // given
        employeeRepository.save(employee);
        String firstName = "Adam";
        String lastName = "Małysz";

        // when
        Employee findedEmployee = employeeRepository.findByJPQLNamedParams(firstName, lastName);

        // then
        assertThat(findedEmployee).isNotNull();
        assertThat(findedEmployee).isEqualTo(employee);
    }

    @DisplayName("testy dla zapytań za pomocą JPQL z bindowaniem parametrów")
    @Test
    void givenFirstNameAndLastName_whenFindByNativeSQL_thenReturnedEmployee() {
        // given
        employeeRepository.save(employee);
        String firstName = "Adam";
        String lastName = "Małysz";

        // when
        Employee findedEmployee = employeeRepository.findByNativeSQL(firstName, lastName);

        // then
        assertThat(findedEmployee).isNotNull();
        assertThat(findedEmployee).isEqualTo(employee);
    }

    @DisplayName("testy dla zapytań za pomocą JPQL z nazwami parametrów")
    @Test
    void givenFirstNameAndLastName_whenFindByJPQLNativeSqlNamedParams_thenReturnedEmployee() {
        // given
        employeeRepository.save(employee);
        String firstName = "Adam";
        String lastName = "Małysz";

        // when
        Employee findedEmployee = employeeRepository.findByJPQLNativeSqlNamedParams(firstName, lastName);

        // then
        assertThat(findedEmployee).isNotNull();
        assertThat(findedEmployee).isEqualTo(employee);
    }
}