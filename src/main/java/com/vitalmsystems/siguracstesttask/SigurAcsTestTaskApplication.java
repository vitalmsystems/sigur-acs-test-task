package com.vitalmsystems.siguracstesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.time.Instant;

@SpringBootApplication
public class SigurAcsTestTaskApplication {

  public static void main(String[] args) {
    System.err.println(Instant.now());
    SpringApplication.run(SigurAcsTestTaskApplication.class, args);
  }

}
