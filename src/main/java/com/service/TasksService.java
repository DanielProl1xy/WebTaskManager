package com.service;

import java.util.NoSuchElementException;
import java.util.Optional;

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

        Comment comment = new Comment();
        comment.setTask(task.getId());
        comment.setAuthor(author.getId());
        comment.setText(text);

        comment = commentReposiory.save(comment);

        return comment;
    }

    public Task createTask(String token, String text, TaskStatus status, TaskPriority priority, User executor) {

        User author = loginService.validateTokenElseThrow(token, true);

        Task task = new Task();
        task.setStatus(status);
        task.setPriority(priority);
        task.setAuthor(author.getId());
        task.setExecutor(executor.getId());
        task.setText(text);
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
        
        task.setExecutor(executor.getId());
        task.setPriority(priority);
        task.setStatus(status);

        task = taskRepository.save(task);

        return task;
    }
    
    public Iterable<Task> getAllTasks() {

        return taskRepository.findAll();
    }

    public Optional<Task> getTaskById(Long taskId) {
        return taskRepository.findById(taskId);
    }


}
