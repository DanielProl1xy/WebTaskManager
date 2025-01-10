package com;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.service.LoginService;
import com.service.TasksService;

@SpringBootTest
public class MyTest {

    @Autowired
    private LoginService loginService;
    private TasksService tasksService;
    
    @Test
    public void FirstTest() {

    }
}
