package com.vitalmsystems.siguracstesttask.repositories;

import com.vitalmsystems.siguracstesttask.model.Employee;
import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
}
