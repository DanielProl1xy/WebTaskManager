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
    
    public Comment createCommentOnTask(User author, Long taskid, String text) {
        
        Task task = taskRepository.findById(taskid)
        .orElseThrow(() -> new NoSuchElementException("Couldn't find the task with ID " + taskid)
        );

        Comment comment = new Comment(task.getId(), author, text);

        comment = commentReposiory.save(comment);

        return comment;
    }

    public Task createTask(String text, TaskStatus status, TaskPriority priority, User author, User executor) {
        Task task = new Task(author, executor, text, status, priority);
        task = taskRepository.save(task);
        return task;
    }

}
