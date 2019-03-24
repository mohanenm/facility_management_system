package com.fms.domainLayer.SpringWiring;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {

  // ApplicationContext context = new ClassPathXmlApplicationContext("Spring_config.xml");

  @Test
  public void roomContext() {

    String config[] = {"Spring_config.xml"};
    ApplicationContext ctx = new ClassPathXmlApplicationContext(config);

    //        //Spring to inject the right object implementation in CustomerService for customer
    // using Setter Injection
    //        //Also, bootstrapping the CustomerService instantiation using factory
    //        IRoom room = (IRoom) context.getBean("secretOfUniverseRoom");
    //        assertEquals(room.getId(), 42);
  }
}
