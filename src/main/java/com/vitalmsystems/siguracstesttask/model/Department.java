package com.vitalmsystems.siguracstesttask.model;

import java.util.HashSet;

public class Department {

  private Long id;
  private String name;
  private final HashSet<Employee> employees = new HashSet<>();

}
