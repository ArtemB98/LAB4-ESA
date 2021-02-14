package com.app.repos;

import org.springframework.data.repository.CrudRepository;
import com.app.entity.Department;

public interface DepartmentRepo extends CrudRepository<Department, Long> {
}
