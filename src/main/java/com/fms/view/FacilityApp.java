package com.fms.view;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class FacilityApp {
    ApplicationContext context = new ClassPathXmlApplicationContext(
            "file:src/main/java/META_INF/FacilityAppBeans.xml");

    //facilityService = context.getBean("")
    //facilityService = context.getBean("")
}
