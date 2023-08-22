package com.ahumadamob.todolist.service;

import java.util.List;

import com.ahumadamob.todolist.entity.Task;

public interface ITaskService {
	void save(Task task);
	void delete(Integer id);
	List<Task> findAll();
	Task findById(Integer id);
	List<Task> findPending();
	List<Task> findCompleted();
	List<Task> findByProjectId(Integer id);
	List<Task> findPendingByProjectId(Integer id);
	List<Task> findCompletedByProjectId(Integer id);
}
