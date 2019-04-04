package com.project;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminApplicationTest {

        @Autowired
        ApplicationContext context;

        @Test
        public  void testIsExit() {
            boolean b = context.containsBean("test");
            System.out.println(b);
        }
}
