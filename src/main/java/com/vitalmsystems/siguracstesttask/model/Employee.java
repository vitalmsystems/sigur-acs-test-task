package com.vitalmsystems.siguracstesttask.model;

import java.time.LocalDate;

public class Employee extends Person {

  private final LocalDate hireTime;
  private LocalDate firedTime;
  private Long departmentId;

  public Employee(LocalDate hireTime, Long departmentId) {
    this.hireTime = hireTime;
    this.departmentId = departmentId;
  }

}
