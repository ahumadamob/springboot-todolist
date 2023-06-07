package com.ahumadamob.todolist.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ahumadamob.todolist.entity.Project;
import com.ahumadamob.todolist.entity.Task;
import com.ahumadamob.todolist.service.IProjectService;
import com.ahumadamob.todolist.service.ITaskService;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
	
	@Autowired
	private ITaskService service;
	
	@GetMapping("/all")
	public ResponseEntity<APIResponse<List<Task>>> getAllTasks(){
		List<Task> tasks = service.findAll();
		if(tasks.isEmpty()) {
			APIResponse<List<Task>> response = new APIResponse<List<Task>>(200, addSingleMessage("No hay tareas"), tasks);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}else {
			APIResponse<List<Task>> response = new APIResponse<List<Task>>(200, null, tasks);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	};	
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Task>> getTaskById(@PathVariable("id") Integer id){
		Task task = service.findById(id);
		if(project == null) {
			APIResponse<Project> response = new APIResponse<Project>(200, addSingleMessage("No se encontró el proyecto especificado"), project);
			return ResponseEntity.status(HttpStatus.OK).body(response);			
		}else {
			APIResponse<Project> response = new APIResponse<Project>(200, null, project);
			return ResponseEntity.status(HttpStatus.OK).body(response);
		}
	};	
	
    @PostMapping
    public ResponseEntity<APIResponse<Project>> createProject(@RequestBody Project project, BindingResult result) {
        service.save(project);
        APIResponse<Project> response = new APIResponse<Project>(201, addSingleMessage("Proyecto creado satisfactoriamente"), project);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }	

	@PutMapping
	public ResponseEntity<APIResponse<Project>> modifyProject(@RequestBody Project project) {
		if(exists(project.getId())) {
			service.save(project);
	        APIResponse<Project> response = new APIResponse<Project>(200, addSingleMessage("Proyecto modificado"), project);
	        return ResponseEntity.status(HttpStatus.OK).body(response);				
		}else {
			APIResponse<Project> response = new APIResponse<Project>(200, addSingleMessage("No se encontró el proyecto especificado"), project);
			return ResponseEntity.status(HttpStatus.OK).body(response);	
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Project>> deleteProject(@PathVariable("id") Integer id){
		if(exists(id)) {
			APIResponse<Project> response = new APIResponse<Project>(200, addSingleMessage("No se encontró el proyecto especificado"), null);
			return ResponseEntity.status(HttpStatus.OK).body(response);				
		}else {
			service.delete(id);
	        APIResponse<Project> response = new APIResponse<Project>(200, addSingleMessage("Proyecto eliminado"), null);
	        return ResponseEntity.status(HttpStatus.OK).body(response);			
		}
	}
	
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
    	List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        APIResponse<Project> response = new APIResponse<Project>(400, errors, null);
        return ResponseEntity.badRequest().body(response);
    }
    
    private List<String> addSingleMessage(String message) {
    	List<String> messages = new ArrayList<>();
    	messages.add(message);
    	return messages;
    }
    
    private boolean exists(Integer id) {
    	if(id == null) {
    		return false;
    	}else {
    		Project project = service.findById(id);
    		if(project == null) {
    			return false;
    		}else {
    			return true;
    		}
    	}
    }

}
