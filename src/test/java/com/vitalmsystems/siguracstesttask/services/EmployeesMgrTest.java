package com.vitalmsystems.siguracstesttask.services;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.ScheduledAnnotationBeanPostProcessor;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.awaitility.Awaitility.await;

@SpringBootTest
class EmployeesMgrTest {

    @Autowired
    private ApplicationContext context;


    @Test
    void reportCurrentTime() {
    }

    @Test
    public void stopEmployeeMgrScheduling() throws Exception {
        ScheduledAnnotationBeanPostProcessor bean = context.getBean(ScheduledAnnotationBeanPostProcessor.class);
        EmployeesMgr schedulerBean = context.getBean(EmployeesMgr.class);

        await().untilAsserted(() -> {
            System.err.println("Checking");
            Assertions.assertEquals(false, schedulerBean.getEnabled().get());
        });

        bean.postProcessBeforeDestruction(schedulerBean, "EmployeesMgr");

        await().atLeast(3, SECONDS);

        Assertions.assertEquals(false, schedulerBean.getEnabled().get());

        System.err.println("done");
    }
}