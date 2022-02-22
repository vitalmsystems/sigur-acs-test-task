package com.vitalmsystems.siguracstesttask.managers;

import com.vitalmsystems.siguracstesttask.model.Department;
import com.vitalmsystems.siguracstesttask.model.Employee;
import com.vitalmsystems.siguracstesttask.repositories.DepartmentRepository;
import com.vitalmsystems.siguracstesttask.repositories.EmployeeRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class EmployeesMgr {

  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;
  private final GuestsMgr guestsMgr;
  private final PassEmulator passEmulator;

  private final static AtomicInteger dayCounter = new AtomicInteger(1);
  private final static int firingRatio = 5;

  public EmployeesMgr(DepartmentRepository departmentRepository,
                      EmployeeRepository employeeRepository,
                      GuestsMgr guestsMgr,
                      PassEmulator passEmulator) {
    this.departmentRepository = departmentRepository;
    this.employeeRepository = employeeRepository;
    this.guestsMgr = guestsMgr;
    this.passEmulator = passEmulator;
  }

  public void generateNewEmployee(LocalDate currentDate, LocalDate endDate) {
    String msg = String.format("%n-------- И пришёл новый виртуальный день: %s --------------", currentDate);
    System.out.println(msg);
    Iterable<Department> departmentsIterable = departmentRepository.findAll();
    ArrayList<Department> departments = new ArrayList<>();
    departmentsIterable.iterator().forEachRemaining(departments::add);

    int departmentRandomIndex = ThreadLocalRandom.current().nextInt(departments.size());
    Department department = departments.get(departmentRandomIndex);
    LocalDate hireTime = between(currentDate, endDate);
    Employee employee = new Employee(hireTime, null, department, null);
    byte[] card = passEmulator.generateCardCode();
    employee.setCard(card);
    department.getEmployees().add(employee);

    Employee savedEmployee = guestsMgr.createOrNotGuest(employee, currentDate);

    generateHiringLogMessage(savedEmployee, currentDate);

    if (dayCounter.getAndIncrement() % firingRatio == 0) {
      fireSomeEmployees(currentDate);
    }
  }

  private void fireSomeEmployees(LocalDate currentDate) {
    int numberEmployeesToFire = ThreadLocalRandom.current().nextInt(1, 4);

    List<Employee> firedEmployees = new ArrayList<>();

    List<Employee> byFiredDateIsNull = employeeRepository.findByFiredTimeIsNull();
    for (int i = 0; i < numberEmployeesToFire; i++) {
      Employee employeeToFire = byFiredDateIsNull.get(ThreadLocalRandom.current().nextInt(byFiredDateIsNull.size()));

      if (employeeToFire != null) {
        LocalDate firedDate = employeeToFire.getHireTime().plusDays(ThreadLocalRandom.current().nextInt(200));
        employeeToFire.setFiredTime(firedDate);
        employeeRepository.save(employeeToFire);
        byFiredDateIsNull.remove(employeeToFire);

        firedEmployees.add(employeeToFire);

        generateFiringLogMessage(employeeToFire, currentDate);
      }
    }

    guestsMgr.cancelVisitsOfFiredEmployees(firedEmployees);
  }

  private void generateFiringLogMessage(Employee employeeToFire, LocalDate currentDate) {
    LocalDate firedTime = employeeToFire.getFiredTime();
    LocalDate hireTime = employeeToFire.getHireTime();
    String departmentName = employeeToFire.getDepartment().getName();
    long daysWorked = ChronoUnit.DAYS.between(hireTime, firedTime);

    String message = String.format("%s. Сотрудник %d уволен %s. Отдел: %s. Проработал: %d дней.",
        currentDate, employeeToFire.getId(), firedTime, departmentName, daysWorked);
    System.err.println(message);
  }

  private void generateHiringLogMessage(Employee employeeToHire, LocalDate currentDate) {
    LocalDate hireTime = employeeToHire.getHireTime();
    String departmentName = employeeToHire.getDepartment().getName();

    String message = String.format("%s. Сотрудник %d нанят %s. Отдел: %s.",
        currentDate, employeeToHire.getId(), hireTime, departmentName);
    System.out.println(message);
  }

  private static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {
    long startEpochDay = startInclusive.toEpochDay();
    long endEpochDay = endExclusive.toEpochDay();

    long randomDay = ThreadLocalRandom.current()
        .nextLong(startEpochDay, endEpochDay);

    return LocalDate.ofEpochDay(randomDay);
  }
}
