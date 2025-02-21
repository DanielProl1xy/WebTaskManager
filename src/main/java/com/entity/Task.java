package com.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import com.fasterxml.jackson.annotation.JsonProperty;

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
    @JsonProperty
    private Long id;

    @ManyToOne
    @JsonProperty
    private Long author;

    @ManyToOne
    @JsonProperty
    private Long executor;

    @JsonProperty
    private String text;

    @JsonProperty
    private TaskStatus status;

    @JsonProperty
    private TaskPriority priority;

    public Long getId() {
        return id;
    }

    public Long getAuthor() {
        return author;
    }

    public Long getExecutor() {
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

    public void setAuthor(Long author) {
        this.author = author;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setExecutor(Long executor) {
        this.executor = executor;
    }

    public void setPriority(TaskPriority priority) {
        this.priority = priority;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }
}
