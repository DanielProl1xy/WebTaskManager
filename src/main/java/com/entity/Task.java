package com.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;

import jakarta.persistence.ManyToOne;

@Table("task")
public class Task {
    
    public enum TaskStatus {
        WAITING,
        INPROCESS,
        FINISHED
    }

    public enum TaskPriority {
        LOW,
        MID,
        HIGH
    }

    @Id
    private Long id;

    @ManyToOne
    private final User author;

    private User executor;

    private String text;

    private TaskStatus status;

    private TaskPriority priority;



    public Task(User author,
                User executor,
                String text,
                TaskStatus status,
                TaskPriority priority) {
        this.author = author;
        this.executor = executor;
        this.text = text;
        this.status = status;
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public User getAuthor() {
        return author;
    }

    public User getExecutor() {
        return executor;
    }
    
    public String getText() {
        return text;
    }

    public TaskPriority getPriority() {
        return priority;
    }

    public TaskStatus getStatus() {
        return status;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setExecutor(User executor) {
        this.executor = executor;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @PersistenceCreator
    private Task(Long id, 
                User author,
                User executor,
                String text,
                TaskStatus status,
                TaskPriority priority) {
        this.id = id;
        this.author = author;
        this.executor = executor;
        this.text = text;
        this.status = status;
        this.priority = priority;
    }
}
