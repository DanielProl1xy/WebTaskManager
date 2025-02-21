package com.controller;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Comment;
import com.entity.Task;
import com.entity.Token;
import com.entity.User;
import com.entity.Task.TaskPriority;
import com.entity.Task.TaskStatus;
import com.service.LoginService;
import com.service.TasksService;
import com.service.LoginService.LoginForm;

import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class APITasksManagerController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TasksService tasksService;

    @PostMapping("/login")
    public ResponseEntity<?> loginWithForm(@RequestBody LoginForm loginForm) {

        Token token = loginService.loginWithForm(loginForm);

        return ResponseEntity.ok().body(token.getToken());
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerWithForm(@RequestBody LoginForm loginForm) {
        Token token = loginService.registerWithForm(loginForm);

        return ResponseEntity.ok().body(token.getToken());
    }    
    
    @PostMapping("/create")
    public ResponseEntity<Task> createTask(@RequestParam String token,
                                        @RequestParam String text,
                                        @RequestParam TaskStatus status,
                                        @RequestParam TaskPriority priority,
                                        @RequestParam Long executorId) {

        User executorUser = loginService.getUserById(executorId);

        Task task = tasksService.createTask(token, text, status, priority, executorUser);

        return ResponseEntity.ok().body(task);
    }

    @PostMapping("/comment")
    public ResponseEntity<?> createCommentOnTask(@RequestParam String token,
                                @RequestParam Long taskId,
                                @RequestParam String text) {
            
        Comment comment = tasksService.createCommentOnTask(token, taskId, text);

        return ResponseEntity.ok().body(comment);
    }

    @PostMapping("/update")
    public ResponseEntity<?> updateTask(@RequestParam String token,
                            @RequestParam Long taskId,
                            @RequestParam TaskStatus status,
                            @RequestParam TaskPriority priority,
                            @RequestParam Long executorId) {
        
        User executorUser = loginService.getUserById(executorId);

        Task task = tasksService.updateTask(token, taskId, status, priority, executorUser);

        return ResponseEntity.ok().body(task);
    }

    @GetMapping("/view")
    public ResponseEntity<?> postMethodName(@RequestParam(required = false) Long taskId) {

        if(taskId != null) {
            Task task = tasksService.getTaskById(taskId).orElseThrow(() -> new IllegalArgumentException("Couldn't find task with given ID"));
            return ResponseEntity.ok().body(task);
        }
        List<Task> tasks = new ArrayList<>();
        tasksService.getAllTasks().forEach(tasks::add);

        return ResponseEntity.ok().body(tasks);

    }
    
}
