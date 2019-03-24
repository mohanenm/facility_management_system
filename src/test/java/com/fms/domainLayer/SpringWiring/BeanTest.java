package com.fms.domainLayer.SpringWiring;

import com.fms.domainLayer.facility.IBuilding;
import com.fms.domainLayer.facility.IFacility;
import com.fms.domainLayer.facility.IRoom;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static junit.framework.TestCase.assertEquals;

public class BeanTest {

  ApplicationContext context = new ClassPathXmlApplicationContext("file:src/main/java/META_INF/Spring_config.xml");

  @Test
  public void roomContext() {

            //Spring to inject the right object implementation in room for IRoom
            //using Setter Injection
            //Also, bootstrapping the Room instantiation using factory
            IRoom secretRoom = (IRoom) context.getBean("secretOfUniverseRoom");
            assertEquals(secretRoom.getId(), 42);

            IRoom room = (IRoom) context.getBean("room");
            assertEquals((room.getId() & room.getCapacity()), 0);
  }

  @Test
  public void buildingContext() {

      IBuilding building = (IBuilding) context.getBean("building");
      System.out.println("Building -> " + building.toString());
      //This demonstrates that we can extract an injected Room ID within a building bean
      assertEquals(building.getRooms().get(0).getId(), 42);
  }

  @Test
  public void facilityContext() {

      IFacility facility = (IFacility) context.getBean("facility");
      System.out.println("Facility -> " + facility.toString());
      //We can get an injected building ID from a facility
      assertEquals(facility.getBuildings().get(0).getId(), 1);
      //And we can get our specific room through two layers of dependency injection
      assertEquals(facility.getBuildings().get(0).getRooms().get(0).getId(), 42);
  }
}
