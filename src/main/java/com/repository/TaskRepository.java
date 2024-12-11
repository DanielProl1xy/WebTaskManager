package com.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.entity.Task;

@Repository
public interface TaskRepository extends CrudRepository<Task, Long> {
    
}
