package com.vitalmsystems.siguracstesttask.model;

import lombok.*;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "departments")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RequiredArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Department {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  @EqualsAndHashCode.Include
  private Long id;

  @Column(name = "name")
  @NonNull
  private String name;

  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "department")
  @ToString.Exclude
  private Set<Employee> employees = new HashSet<>();

}
