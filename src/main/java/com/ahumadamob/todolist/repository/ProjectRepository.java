package com.ahumadamob.todolist.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ahumadamob.todolist.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Integer> {
}
