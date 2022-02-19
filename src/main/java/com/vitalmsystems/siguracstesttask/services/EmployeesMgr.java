package com.vitalmsystems.siguracstesttask.services;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.Month;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicBoolean;

@Service
public class EmployeesMgr {

  private static final Logger log = LoggerFactory.getLogger(EmployeesMgr.class);
//  private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("HH:mm:ss");

  private LocalDate dateStart = LocalDate.of(2022, Month.JANUARY, 1);
  private LocalDate dateEnd = LocalDate.of(2023, Month.JANUARY, 1);
  private LocalDate currentDate = dateStart;

  public AtomicBoolean getEnabled() {
    return enabled;
  }

  private AtomicBoolean enabled = new AtomicBoolean(true);

  @Scheduled(fixedRate = 10)
  public void reportCurrentTime() {
    if (currentDate.isBefore(dateEnd)) {
//      log.info("CurrentDate {} - HireDate {}", currentDate, between(currentDate, dateEnd));
      String message = String.format("CurrentDate %s - HireDate %s", currentDate, between(currentDate, dateEnd));
      System.out.println(message);
      currentDate = currentDate.plusDays(1);
    } else {
      log.info("Employee manager finished his work {}", enabled.get());
      enabled.set(false);
    }
  }

  private static LocalDate between(LocalDate startInclusive, LocalDate endExclusive) {

    long startEpochDay = startInclusive.toEpochDay();
    long endEpochDay = endExclusive.toEpochDay();
    long randomDay = ThreadLocalRandom.current()
            .nextLong(startEpochDay, endEpochDay);

    return LocalDate.ofEpochDay(randomDay);
  }

}