package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.entity.Task;
import com.entity.Token;
import com.entity.User;
import com.entity.Task.TaskPriority;
import com.entity.Task.TaskStatus;
import com.repository.TaskRepository;
import com.service.LoginService;
import com.service.LoginService.LoginForm;

import org.springframework.web.bind.annotation.RequestBody;



@RestController
@RequestMapping("/api")
public class APITasksManagerController {

    @Autowired
    private LoginService loginService;

    @Autowired
    private TaskRepository taskRepository;

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

        Task task = new Task(author, null, text, status, priority);

        task = taskRepository.save(task);

        return ResponseEntity.ok().body(task);
    }
}
