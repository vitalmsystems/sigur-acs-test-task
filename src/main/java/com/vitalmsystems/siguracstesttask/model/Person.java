package com.vitalmsystems.siguracstesttask.model;

import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;

@MappedSuperclass
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString
public class Person implements Serializable, Comparable<Person> {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @EqualsAndHashCode.Include
  private Long id;
  private byte[] card;

  @Override
  public int compareTo(Person anotherPerson) {
    return id.compareTo(anotherPerson.id);
  }
}
