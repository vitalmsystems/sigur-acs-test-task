package com.vitalmsystems.siguracstesttask.managers;

import com.vitalmsystems.siguracstesttask.model.Department;
import com.vitalmsystems.siguracstesttask.model.Employee;
import com.vitalmsystems.siguracstesttask.repositories.DepartmentRepository;
import com.vitalmsystems.siguracstesttask.repositories.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class EmployeesMgr {

  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;
  private final GuestsMgr guestsMgr;

  public EmployeesMgr(DepartmentRepository departmentRepository,
                      EmployeeRepository employeeRepository,
                      GuestsMgr guestsMgr) {
    this.departmentRepository = departmentRepository;
    this.employeeRepository = employeeRepository;
    this.guestsMgr = guestsMgr;
  }

  public void generateNewEmployee(LocalDate currentDate, LocalDate endDate) {
    System.out.println("new day has come: " + currentDate);
    Iterable<Department> departmentsIterable = departmentRepository.findAll();
    ArrayList<Department> departments = new ArrayList<>();
    departmentsIterable.iterator().forEachRemaining(departments::add);

    int departmentRandomIndex = ThreadLocalRandom.current().nextInt(departments.size());
    Department department = departments.get(departmentRandomIndex);
    LocalDate hireTime = between(currentDate, endDate);
    Employee employee = new Employee(hireTime, null, department, null);
    department.getEmployees().add(employee);

    Employee savedEmployee = guestsMgr.createOrNotGuest(employee, currentDate);
    generateHiringLogMessage(savedEmployee, currentDate);
//    System.err.println(savedEmployee);

//    departmentRepository.save(department);
//    departmentRepository.findById(5L).ifPresent(d -> System.out.println(d.getEmployees()));

  }

  private void generateHiringLogMessage(Employee employee, LocalDate currentDate) {
    LocalDate hireTime = employee.getHireTime();
    String departmentName = employee.getDepartment().getName();

    String message = String.format("%s. Сотрудник %d нанят %s. Отдел: %s.",
        currentDate, employee.getId(), hireTime, departmentName);
    System.err.println(message);
  }


  private static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
    long startEpochDay = startInclusive.toEpochDay();
    long endEpochDay = endExclusive.toEpochDay();

    long randomDay = ThreadLocalRandom.current()
        .nextLong(startEpochDay, endEpochDay);

    return LocalDate.ofEpochDay(randomDay);
  }
}
