package com.vitalmsystems.siguracstesttask.repositories;

import com.vitalmsystems.siguracstesttask.model.Employee;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
//  Employee findByEmployee(Employee employee);

  List<Employee> findByFiredTimeIsNull();
}
