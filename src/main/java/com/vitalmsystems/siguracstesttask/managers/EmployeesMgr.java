package com.vitalmsystems.siguracstesttask.managers;

import com.vitalmsystems.siguracstesttask.repositories.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
public class EmployeesMgr {

  private final EmployeeRepository employeeRepository;

  public EmployeesMgr(EmployeeRepository employeeRepository) {
    this.employeeRepository = employeeRepository;
  }

  public void generateNewEmployee(LocalDate date) {
    System.out.println("new day has come: " + date);
  }
}
