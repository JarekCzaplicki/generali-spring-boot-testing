package org.example.demogeneralispringboottesting.repository;

import org.example.demogeneralispringboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository // nie musimy dawać Bean, ponieważ implementacja 'JpaRepository' w postaci 'SimpleJpaRepository' jest oznaczona jako @Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Optional<Employee> findByEmail(String email);
}
