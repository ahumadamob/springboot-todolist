package com.ahumadamob.todolist.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahumadamob.todolist.entity.Task;
import com.ahumadamob.todolist.repository.TaskRepository;
import com.ahumadamob.todolist.service.ITaskService;

@Service
public class TaskServiceImpl implements ITaskService {
	
	@Autowired
	private TaskRepository repository;

	@Override
	public void save(Task task) {
		repository.save(task);
	}

	@Override
	public void delete(Integer id) {		
		repository.deleteById(id);
	}

	@Override
	public List<Task> findAll() {
		return repository.findAll();
	}

	@Override
	public Task findById(Integer id) {	
		Optional<Task> optional = repository.findById(id);
		return optional.orElse(null);
	}

	@Override
	public List<Task> findPending() {
		return repository.findByCompletedFalse();
	}

	@Override
	public List<Task> findCompleted() {
		return repository.findByCompletedTrue();		
	}

	@Override
	public List<Task> findByProjectId(Integer projectId) {
		return repository.findByProjectId(projectId);
	}

	@Override
	public List<Task> findPendingByProjectId(Integer id) {
		return repository.findByCompletedFalseAndProjectId(id);
	}

	@Override
	public List<Task> findCompletedByProjectId(Integer id) {
		return repository.findByCompletedTrueAndProjectId(id);
	}

}
