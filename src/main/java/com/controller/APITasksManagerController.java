package com.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
import com.repository.TaskRepository;
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

    @PostMapping("login")
    public ResponseEntity<?> loginWithForm(@RequestBody LoginForm loginForm) {

        Token token = loginService.loginWithForm(loginForm);

        return ResponseEntity.ok().body(token.getToken());
    }

    @PostMapping("register")
    public ResponseEntity<?> registerWithForm(@RequestBody LoginForm loginForm) {
        Token token = loginService.registerWithForm(loginForm);

        return ResponseEntity.ok().body(token.getToken());
    }    
    
    @PostMapping("taskCreate")
    public ResponseEntity<Task> createTask(@RequestParam String token,
                                        @RequestParam String text,
                                        @RequestParam TaskStatus status,
                                        @RequestParam TaskPriority priority) {

        User author = loginService.validateToken(token, true);

        Task task = tasksService.createTask(text, status, priority, author, null);

        return ResponseEntity.ok().body(task);
    }

    @PostMapping("taskComment")
    public ResponseEntity<?> createCommentOnTask(@RequestParam String token,
                                @RequestParam Long taskId,
                                @RequestParam String text) {
        
        User author = loginService.validateToken(token, false);
    
        Comment comment = tasksService.createCommentOnTask(author, taskId, text);

        return ResponseEntity.ok().body(comment);
    }
    
}
