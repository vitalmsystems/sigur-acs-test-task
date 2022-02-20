package com.vitalmsystems.siguracstesttask.bootstrap;

import com.vitalmsystems.siguracstesttask.model.Department;
import com.vitalmsystems.siguracstesttask.model.Employee;
import com.vitalmsystems.siguracstesttask.model.Guest;
import com.vitalmsystems.siguracstesttask.repositories.DepartmentRepository;
import com.vitalmsystems.siguracstesttask.repositories.EmployeeRepository;
import com.vitalmsystems.siguracstesttask.repositories.GuestRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class LoadDataOnStartup implements CommandLineRunner {

  private final DepartmentRepository departmentRepository;
  private final EmployeeRepository employeeRepository;
  private final GuestRepository guestRepository;

  public LoadDataOnStartup(DepartmentRepository departmentRepository,
                           EmployeeRepository employeeRepository,
                           GuestRepository guestRepository) {
    this.departmentRepository = departmentRepository;
    this.employeeRepository = employeeRepository;
    this.guestRepository = guestRepository;
  }

  @Override
  public void run(String... args) throws Exception {
    // @formatter:off
    List<String> departmentNames = Arrays.asList( "Отдел продаж",
                                                  "Отдел маркетинга",
                                                  "Отдел кадров",
                                                  "Планово-экономический отдел",
                                                  "Транспортная служба",
                                                  "Отдел АСУ",
                                                  "Отдел экспертизы",
                                                  "Юридический отдел",
                                                  "Участок упаковки готовой продукции",
                                                  "Отдел закупок"
    );
    // @formatter:on

    List<Department> departments = departmentNames.stream()
        .map(Department::new)
        .collect(Collectors.toList());

    Employee employee = new Employee(LocalDate.now(), null, departments.get(5), null);
    Guest guest = new Guest(employee, LocalDate.now().plusWeeks(2));
    guest.setCard(new byte[]{'a', '?', Byte.MAX_VALUE});
    employee.setGuest(guest);
    departments.get(3).getEmployees().add(employee);
//    guestRepository.save(guest);
    departmentRepository.saveAll(departments);
//    Iterable<Employee> employees = employeeRepository.findAll();
//    employees.forEach(e -> System.err.println(e.getGuest()));


//    ThreadLocalRandom random = ThreadLocalRandom.current();

//    List<Department> departments = departmentNames.stream()
//        .map(name -> {
//          Department department = new Department(name);
//          IntStream.rangeClosed(1, 4)
//              .forEach(i -> {
//                LocalDate hireDate = LocalDate.now().plusDays(random.nextInt(10));
//                Employee employee = new Employee(hireDate, null, department, null);
//
//                department.getEmployees().add(employee);
//              });
//
//          System.out.println(department.getEmployees().size());
//
//          return department;
//
//        })
//        .collect(Collectors.toList());

//    System.err.println(departments);


//    List<Employee> employees = IntStream.rangeClosed(1, 30)
//        .mapToObj(i -> {
//          LocalDate hireDate = LocalDate.now().plusDays(random.nextInt(150));
//          int departmentIndex = random.nextInt(departments.size());
//          Department department = departments.get(departmentIndex);
//          Employee employee = new Employee(hireDate, null, department, null);
//          department.getEmployees().add(employee);
//
//          if (i % 3 == 0) {
//            Guest guest = new Guest(employee, hireDate.plusDays(10));
//            employee.setGuest(guest);
//          }
//          return employee;
//        }).collect(Collectors.toList());

//    List<Guest> guests = IntStream.rangeClosed(1, 15)
//        .mapToObj(i -> {
//          LocalDate visitDate = LocalDate.now().plusDays(random.nextInt(90));
//          int employeeIndex = random.nextInt(employees.size());
//          Employee employeeToVisit = employees.get(employeeIndex);
//          Guest guest = new Guest(employeeToVisit, visitDate);
////          employeeToVisit.setGuest(guest);
//          return guest;
//        }).collect(Collectors.toList());

//    System.err.println(guests);

//    guests.forEach(guestRepository::save);
//    departments.forEach(departmentRepository::save);
//    employees.forEach(employeeRepository::save);

//    Optional<Employee> employeeById = employeeRepository.findById(3L);
//    System.err.println(employeeById);
//    System.err.println(departmentRepository.findById(3L));
//    departmentRepository.findById(4L).ifPresent(d -> System.err.println(d.getEmployees().size()));

//    Iterable<Department> allDepartments = departmentRepository.findAll();
//    System.err.println(allDepartments);

  }
}
