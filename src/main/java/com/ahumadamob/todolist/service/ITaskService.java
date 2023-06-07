package com.ahumadamob.todolist.service;

import java.util.List;

import com.ahumadamob.todolist.entity.Task;

public interface ITaskService {
	void save(Task task);
	void delete(Integer id);
	List<Task> findAll();
}
