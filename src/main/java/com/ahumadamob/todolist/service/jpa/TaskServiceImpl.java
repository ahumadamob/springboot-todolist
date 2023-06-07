package com.ahumadamob.todolist.service.jpa;

import java.util.List;

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

}
