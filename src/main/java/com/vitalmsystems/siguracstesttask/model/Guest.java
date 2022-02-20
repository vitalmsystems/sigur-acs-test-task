package com.vitalmsystems.siguracstesttask.model;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "guests")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Getter
@Setter
@ToString
public class Guest extends Person {

  @OneToOne(mappedBy = "guest")
  private Employee employee;

  @Column(name = "visit_date")
  private LocalDate visitDate;

}
