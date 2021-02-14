package com.app.repos;

import org.springframework.data.repository.CrudRepository;
import com.app.entity.Employee;

import java.util.List;

public interface EmployeeRepo extends CrudRepository<Employee, Long> {
    List<Employee> findByEmail(String email);
}
