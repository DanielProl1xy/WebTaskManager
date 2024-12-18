package com.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;



@Table("comment")
public class Comment {
    
    @Id
    private Long id;

    private final Long task;

    private final Long author;
    
    private final String text;


    public Comment(Long task, User author, String text) {
        this.task = task;
        this.author = author.getId();
        this.text = text;
    }

    public Long getId() {
        return id;
    }

    public Long getTask() {
        return task;
    }

    public Long getAuthor() {
        return author;
    }

    public String getText() {
        return text;
    }

    @PersistenceCreator
    private Comment(Long id, Long task, Long author, String text) {
        this.id = id;
        this.task = task;
        this.author = author;
        this.text = text;
    }
}
