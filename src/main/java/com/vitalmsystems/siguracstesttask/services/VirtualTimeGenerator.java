package com.vitalmsystems.siguracstesttask.services;

import com.vitalmsystems.siguracstesttask.managers.EmployeesMgr;
import com.vitalmsystems.siguracstesttask.managers.PassEmulator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class VirtualTimeGenerator {

  private static final Logger log = LoggerFactory.getLogger(VirtualTimeGenerator.class);

  private final EmployeesMgr employeesMgr;
  private final PassEmulator passEmulator;

  private LocalDate dateStart = LocalDate.of(2022, Month.JANUARY, 1);
  private LocalDate dateEnd = LocalDate.of(2023, Month.JANUARY, 1);
  private LocalDate currentDate = dateStart;

  public VirtualTimeGenerator(EmployeesMgr employeesMgr,
                              PassEmulator passEmulator) {
    this.employeesMgr = employeesMgr;
    this.passEmulator = passEmulator;
  }

  public AtomicBoolean getEnabled() {
    return enabled;
  }

  private AtomicBoolean enabled = new AtomicBoolean(true);

  @Scheduled(fixedRate = 1000)
  public void newDayHasCome() {
    if (currentDate.isBefore(dateEnd)) {
      employeesMgr.generateNewEmployee(currentDate, dateEnd);

      passEmulator.helloNewDay(currentDate);

      currentDate = currentDate.plusDays(1);
    } else {
      log.info("Employee manager finished his work {}", enabled.get());
      enabled.set(false);
    }
  }
}
