package com.vitalmsystems.siguracstesttask;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SigurAcsTestTaskApplication {

  public static void main(String[] args) {
    SpringApplication.run(SigurAcsTestTaskApplication.class, args);
  }

}
