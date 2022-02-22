package com.vitalmsystems.siguracstesttask.managers;

import com.vitalmsystems.siguracstesttask.model.Employee;
import com.vitalmsystems.siguracstesttask.model.Guest;
import com.vitalmsystems.siguracstesttask.model.Person;
import com.vitalmsystems.siguracstesttask.repositories.EmployeeRepository;
import com.vitalmsystems.siguracstesttask.repositories.GuestRepository;
import org.springframework.stereotype.Component;

import javax.xml.bind.DatatypeConverter;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

@Component
public class PassEmulator {

  private final EmployeeRepository employeeRepository;
  private final GuestRepository guestRepository;

  public PassEmulator(EmployeeRepository employeeRepository, GuestRepository guestRepository) {
    this.employeeRepository = employeeRepository;
    this.guestRepository = guestRepository;
  }

  public void helloNewDay(LocalDate currentDate) {
    ThreadLocalRandom random = ThreadLocalRandom.current();

    for (int i = 0; i < 10; i++) {
      if (random.nextInt(0, 100) < 20) {
        checkIfCardFound(generateCardCode(), currentDate);
      } else {
        List<Person> allPersons = getAllPersons();
        Person randomPerson = allPersons.get(ThreadLocalRandom.current().nextInt(allPersons.size()));
        checkIfCardFound(randomPerson.getCard(), currentDate);
      }
    }
  }

  private void checkIfCardFound(byte[] card, LocalDate currentDate) {
    List<Person> persons = getAllPersons();
    Optional<Person> found = persons.stream().filter(person -> Arrays.equals(card, person.getCard())).findAny();

    if (found.isPresent()) {
      generateAccessLogMessage(found.get(), currentDate);
    } else {
      generateCardNotFoundLogMessage(card);
    }
  }

  private void generateAccessLogMessage(Person person, LocalDate currentDate) {
    if (person.getClass().equals(Employee.class)) {
      Employee employee = (Employee) person;
      String departmentName = employee.getDepartment().getName();
      String cardHexString = DatatypeConverter.printHexBinary(employee.getCard());
      if (employee.getFiredTime() == null) {
        String message = String.format("%s Предоставлен доступ сотруднику %d. Отдел: %s. Карта: %s.",
            currentDate, employee.getId(), departmentName, cardHexString);
        System.out.println(message);
      } else {
        String message = String.format("%s Доступ запрещён сотруднику %d. Отдел: %s. Карта: %s.",
            currentDate, employee.getId(), departmentName, cardHexString);
        System.err.println(message);
      }
    } else if (person.getClass().equals(Guest.class)) {
      Guest guest = (Guest) person;
      Employee employeeToVisit = guest.getEmployee();
      String departmentName = employeeToVisit.getDepartment().getName();
      String guestCardHexString = DatatypeConverter.printHexBinary(guest.getCard());
      if (employeeToVisit.getFiredTime() == null) {
        String message = String.format("%s Предоставлен доступ гостю %d. Пришёл к %d из отдела: %s. Карта: %s.",
            currentDate, guest.getId(), employeeToVisit.getId(), departmentName, guestCardHexString);
        System.out.println(message);
      } else {
        String message = String.format("%s Доступ запрещён гостю %d. Карта: %s.",
            currentDate, guest.getId(), guestCardHexString);
        System.err.println(message);
      }
    } else {
      System.err.println("Неизвестная сущность пытается получить доступ на предприятие.");
    }

  }

  private void generateCardNotFoundLogMessage(byte[] card) {
    String message = String.format("Поднесена неизвестная карта: %s.", DatatypeConverter.printHexBinary(card));
    System.err.println(message);
  }

  private List<Person> getAllPersons() {
    Iterable<Employee> employees = employeeRepository.findAll();
    Iterable<Guest> guests = guestRepository.findAll();
    List<Person> persons = new ArrayList<>();
    employees.iterator().forEachRemaining(persons::add);
    guests.iterator().forEachRemaining(persons::add);
    return persons;
  }

  public byte[] generateCardCode() {
    byte[] bytes = new byte[16];

    try {
      SecureRandom.getInstanceStrong().nextBytes(bytes);
    } catch (NoSuchAlgorithmException e) {
      System.err.println("Не обнаружен алгоритм отвечающий требованиям цифровой безопасности.");
    }

    return bytes;
  }
}
