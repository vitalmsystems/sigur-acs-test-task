package com.vitalmsystems.siguracstesttask.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "employees")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Employee extends Person {

  @Column(name = "hire_time")
  private LocalDate hireTime;

  @Column(name = "fired_time")
  private LocalDate firedTime;

  @ManyToOne
  @JoinColumn(name = "department_id")
  private Department department;

  @OneToOne(cascade = CascadeType.ALL)
  @JoinColumn(name = "guest_id")
  @ToString.Exclude
  private Guest guest;

}
