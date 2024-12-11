package com.repository;

import java.util.Set;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.Comment;

@Repository
public interface CommentReposiory extends CrudRepository<Comment, Long> {
    
    public Set<Comment> findAllByTask(Long taskid);
}
