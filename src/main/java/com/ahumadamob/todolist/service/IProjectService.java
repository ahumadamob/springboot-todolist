package com.ahumadamob.todolist.service;

import java.util.List;

import com.ahumadamob.todolist.entity.Project;

public interface IProjectService {
	void save(Project project);
	void delete(Integer id);
	List<Project> findAll();
	Project findById(Integer id);
}
