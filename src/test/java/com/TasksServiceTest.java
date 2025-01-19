package com;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.service.LoginService.LoginForm;
import com.entity.Token;
import com.entity.User;
import com.entity.Comment;
import com.entity.Task;
import com.entity.Task.TaskPriority;
import com.entity.Task.TaskStatus;
import com.entity.User.Role;
import com.service.LoginService;
import com.service.TasksService;

@SpringBootTest
public class TasksServiceTest {
    
    @Autowired
    private TasksService tasksService;

    @Autowired
    private LoginService loginService;

    private Token executorToken;
    private Token adminToken;

    private User executorUser;
    private User adminUser;

    private Task createdTask;

    @BeforeAll
    public void setUp() {

        // Creaeate admin user
        LoginForm adminForm = new LoginForm("admin@test.net", 0);
        adminToken = loginService.registerWithForm(adminForm);
        assertNotNull(adminToken);   
        adminUser = loginService.getUserById(adminToken.getUserid());
        assertNotNull(adminUser);

        // Give admin role
        loginService.changeRole(adminUser, Role.ADMIN);

        // Create default user
        LoginForm userForm = new LoginForm("user@test.net", 0);
        executorToken = loginService.registerWithForm(userForm);
        assertNotNull(executorToken);
        executorUser = loginService.getUserById(executorToken.getUserid());
        assertNotNull(executorUser);
    }

    @Test
    public void testCreateTask() {

        String taskText = "Test task";
        createdTask = tasksService.createTask(adminToken.getToken(), taskText, TaskStatus.WAITING, TaskPriority.LOW, executorUser);

        assertNotNull(createdTask);
        assertEquals(taskText, createdTask.getText());
        assertEquals(createdTask.getAuthor(), adminUser);
        assertEquals(createdTask.getExecutor(), executorUser);
    }

    @Test
    public void testCommentTask() {

        assertNotNull(createdTask);

        String text = "This is test comment!";
        Comment comment = tasksService.createCommentOnTask(executorToken.getToken(), createdTask.getId(), text);

        assertNotNull(comment);
        assertEquals(text, comment.getText());
        assertEquals(comment.getAuthor(), executorUser);
    }
}
