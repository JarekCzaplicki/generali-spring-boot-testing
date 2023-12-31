package org.example.demogeneralispringboottesting.repository;

import org.example.demogeneralispringboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

//@Repository // nie musimy dawać Bean, ponieważ implementacja 'JpaRepository' w postaci 'SimpleJpaRepository' jest oznaczona jako @Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);

    // Własne zapytanie używające JPQL ze zbindowanymi zmiennymi
    @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
    Employee findByJPQL(String firstName, String lastName);
    @Query("select e from Employee e where e.firstName = :xyz and e.lastName = :lastName")
    Employee findByJPQLNamedParams(@Param("xyz")String firstName, String lastName); // @Param() dodajemy tylko gdy nazwa parametru jest inna niż nazwa pola

    @Query(value = "select * from employees e where e.first_name = ?1 and e.last_name = ?2"
            , nativeQuery = true)
    Employee findByNativeSQL(String firstName, String lastName);
    @Query(value = "select * from employees e where e.first_name = :xyz and e.last_name = :lastName"
            , nativeQuery = true)
    Employee findByJPQLNativeSqlNamedParams(@Param("xyz")String firstName, String lastName);
}
