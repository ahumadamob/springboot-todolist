package com.ahumadamob.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahumadamob.todolist.entity.Task;

public interface TaskRepository extends JpaRepository<Task, Integer> {

}
