package com.vitalmsystems.siguracstesttask.managers;

import com.vitalmsystems.siguracstesttask.model.Employee;
import com.vitalmsystems.siguracstesttask.model.Guest;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class GuestsMgr {

  private static final int SIX_MONTH = 6;

  public void createOrNotGuest(Employee employee) {

    ThreadLocalRandom random = ThreadLocalRandom.current();

    if (random.nextInt(0, 100) < 50) {
//      Guest guest = new Guest(employee, employee.getHireTime().plusDays(1));
      Guest guest = new Guest(employee, notLaterSixMonths(employee.getHireTime()));
      employee.setGuest(guest);
      System.err.println(guest);
    }
  }

  private static LocalDate notLaterSixMonths(LocalDate startInclusive) {
    long startEpochDay = startInclusive.toEpochDay();
    long endEpochDay = startInclusive.plusMonths(SIX_MONTH).toEpochDay();

    long randomDay = ThreadLocalRandom.current()
        .nextLong(startEpochDay, endEpochDay);

    return LocalDate.ofEpochDay(randomDay);
  }
}
