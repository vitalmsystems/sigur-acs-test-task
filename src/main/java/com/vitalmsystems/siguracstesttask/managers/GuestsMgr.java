package com.vitalmsystems.siguracstesttask.managers;

import com.vitalmsystems.siguracstesttask.model.Employee;
import com.vitalmsystems.siguracstesttask.model.Guest;
import com.vitalmsystems.siguracstesttask.repositories.DepartmentRepository;
import com.vitalmsystems.siguracstesttask.repositories.EmployeeRepository;
import com.vitalmsystems.siguracstesttask.repositories.GuestRepository;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class GuestsMgr {

  private static final int SIX_MONTHS = 6;
  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;
  private final GuestRepository guestRepository;
  private final PassEmulator passEmulator;
  private static final ThreadLocalRandom random = ThreadLocalRandom.current();

  public GuestsMgr(DepartmentRepository departmentRepository,
                   EmployeeRepository employeeRepository,
                   GuestRepository guestRepository,
                   PassEmulator passEmulator) {
    this.departmentRepository = departmentRepository;
    this.employeeRepository = employeeRepository;
    this.guestRepository = guestRepository;
    this.passEmulator = passEmulator;
  }

  public Employee createOrNotGuest(Employee employee, LocalDate currentDate) {
    if (random.nextInt(0, 100) < 50) {
      Guest guest = new Guest(employee, notLaterSixMonths(employee.getHireTime()));
      byte[] card = passEmulator.generateCardCode();
      guest.setCard(card);
      employee.setGuest(guest);
    }

    Employee savedEmployee = employeeRepository.save(employee);

    if (savedEmployee.getGuest() != null)
      generateVisitLogMessage(savedEmployee, currentDate);

    return savedEmployee;
  }

  private void generateVisitLogMessage(Employee employee, LocalDate currentDate) {
    String departmentName = employee.getDepartment().getName();
    Guest guest = employee.getGuest();
    LocalDate visitDate = guest.getVisitDate();
    long daysBeforeVisit = ChronoUnit.DAYS.between(currentDate, visitDate);

    String message = String.format("Гостю %d назначена встреча сотруднику %d. " +
            "Отдел: %s. Дата: %s. До встречи осталось: %d дней.",
        guest.getId(), employee.getId(), departmentName, visitDate, daysBeforeVisit);
    System.err.println(message);
  }

  public void checkIfVisitIsValid(Employee employee, LocalDate currentDate) {

  }

  private static LocalDate notLaterSixMonths(LocalDate startInclusive) {
    long startEpochDay = startInclusive.toEpochDay();
    long endEpochDay = startInclusive.plusMonths(SIX_MONTHS).toEpochDay();

    long randomDay = ThreadLocalRandom.current()
        .nextLong(startEpochDay, endEpochDay);

    return LocalDate.ofEpochDay(randomDay);
  }
}
