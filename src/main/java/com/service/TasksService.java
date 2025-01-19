package com.service;

import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.entity.Comment;
import com.entity.Task;
import com.entity.Task.TaskPriority;
import com.entity.Task.TaskStatus;
import com.entity.User;
import com.repository.CommentReposiory;
import com.repository.TaskRepository;



@Service
public class TasksService {

    @Autowired
    private CommentReposiory commentReposiory;

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private LoginService loginService;
    
    public Comment createCommentOnTask(String token, Long taskId, String text) {
        
        User author = loginService.validateTokenElseThrow(token, false);

        Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NoSuchElementException("Couldn't find the task with ID " + taskId)
        );

        Comment comment = new Comment(task.getId(), author, text);

        comment = commentReposiory.save(comment);

        return comment;
    }

    public Task createTask(String token, String text, TaskStatus status, TaskPriority priority, User executor) {

        User author = loginService.validateTokenElseThrow(token, true);

        Task task = new Task(author, executor, text, status, priority);
        task = taskRepository.save(task);
        return task;
    }

    public Task updateTask(String token, Long taskId,
                        TaskStatus status,
                        TaskPriority priority,
                        User executor) {

        loginService.validateTokenElseThrow(token, true);
        
        Task task = taskRepository.findById(taskId)
        .orElseThrow(() -> new NoSuchElementException("Couldn't find the task with ID " + taskId)
        );  
        
        task.setExecutor(executor);
        task.setPriority(priority);
        task.setStatus(status);

        task = taskRepository.save(task);

        return task;
    }
    
    public Iterable<Task> getAllTasks(String token) {
        loginService.validateTokenElseThrow(token, false);

        return taskRepository.findAll();
    }


}
