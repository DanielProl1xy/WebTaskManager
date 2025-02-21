package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.service.TasksService;



@Controller
public class TaskManagerController {
    

    @Autowired
    private TasksService tasksService;

    @GetMapping("/login")
    public String loginView() {
        return "login";
    }

    @PostMapping("/tasks")
    public String tasksView(Model model) {
        model.addAttribute("tasks", tasksService.getAllTasks());
        return "allTasks";
    }
}
