package com.fms.domainLayer.SpringWiring;

import com.fms.domainLayer.facility.IRoom;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static junit.framework.TestCase.assertEquals;

public class BeanTest {

  // ApplicationContext context = new ClassPathXmlApplicationContext("Spring_config.xml");

  @Test
  public void roomContext() {

    ApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/java/META_INF/Spring_config.xml");

            //Spring to inject the right object implementation in CustomerService for customer
            //using Setter Injection
            //Also, bootstrapping the CustomerService instantiation using factory
            IRoom room = (IRoom) context.getBean("secretOfUniverseRoom");
            assertEquals(room.getId(), 42);
  }
}
