package com.vitalmsystems.siguracstesttask;

import com.vitalmsystems.siguracstesttask.services.VirtualTimeGenerator;
import org.junit.jupiter.api.Assertions;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@SpringBootApplication
@EnableScheduling
@Order(value = 2)
public class SigurAcsTestTaskApplication implements CommandLineRunner {

  private final ApplicationContext context;

  public SigurAcsTestTaskApplication(ApplicationContext context) {
    this.context = context;
  }

  public static void main(String[] args) {
    SpringApplication.run(SigurAcsTestTaskApplication.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    ScheduledAnnotationBeanPostProcessor bean = context.getBean(ScheduledAnnotationBeanPostProcessor.class);
    VirtualTimeGenerator schedulerBean = context.getBean(VirtualTimeGenerator.class);

    await().atMost(370, SECONDS).untilAsserted(() -> {
//      System.err.println("Checking");
      Assertions.assertEquals(false, schedulerBean.getEnabled().get());
      System.err.println("Assertion successful.");
    });

    bean.postProcessBeforeDestruction(schedulerBean, "VirtualTimeGenerator");

  }
}
