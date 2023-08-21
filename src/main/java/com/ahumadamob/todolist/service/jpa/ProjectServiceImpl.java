package com.ahumadamob.todolist.service.jpa;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ahumadamob.todolist.entity.Project;
import com.ahumadamob.todolist.repository.ProjectRepository;
import com.ahumadamob.todolist.service.IProjectService;

@Service
public class ProjectServiceImpl implements IProjectService {

	@Autowired
	private ProjectRepository repository;
	
	@Override
	public void save(Project project) {
		repository.save(project);
	}

	@Override
	public void delete(Integer id) {
		repository.deleteById(id);
	}

	@Override
	public List<Project> findAll() {
		return repository.findAll();
	}

	@Override
	public Project findById(Integer id) {
		Optional<Project> optional = repository.findById(id);
		if(optional.isPresent()) {
			return optional.get();
		}else {
			return null;
		}
	}

}
