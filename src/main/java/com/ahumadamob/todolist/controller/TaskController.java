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

import com.ahumadamob.todolist.entity.Task;
import com.ahumadamob.todolist.service.ITaskService;
import com.ahumadamob.todolist.util.ResponseUtil;

import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/v1/task")
public class TaskController {
	
	@Autowired
	private ITaskService service;
	
	@GetMapping("/all")
	public ResponseEntity<APIResponse<List<Task>>> getAllTasks() {
	    List<Task> tasks = service.findAll();
	    return tasks.isEmpty() ? ResponseUtil.buildNotFoundResponse("No hay tareas")
	            : ResponseUtil.buildSuccessResponse(tasks);
	}	
	
	@GetMapping("/all/{id}")
	public ResponseEntity<APIResponse<List<Task>>> getAllTasksByProyectId(@PathVariable("id") Integer id) {
	    List<Task> tasks = service.findByProjectId(id);
	    return tasks.isEmpty() ? ResponseUtil.buildNotFoundResponse("No hay tareas")
	            : ResponseUtil.buildSuccessResponse(tasks);
	}	
	
	@GetMapping("/pending")
	public ResponseEntity<APIResponse<List<Task>>> getPendingTasks() {
	    List<Task> tasks = service.findPending();
	    return tasks.isEmpty() ? ResponseUtil.buildNotFoundResponse("No hay tareas pendientes")
	            : ResponseUtil.buildSuccessResponse(tasks);
	}
	
	
	@GetMapping("/pending/{id}")
	public ResponseEntity<APIResponse<List<Task>>> getPendingTasksByProjectId(@PathVariable("id") Integer id) {
	    List<Task> tasks = service.findPendingByProjectId(id);
	    return tasks.isEmpty() ? ResponseUtil.buildNotFoundResponse("No hay tareas pendientes")
	            : ResponseUtil.buildSuccessResponse(tasks);
	}	
	
	@GetMapping("/completed")
	public ResponseEntity<APIResponse<List<Task>>> getCompletedTasks() {
	    List<Task> tasks = service.findCompleted();
	    return tasks.isEmpty() ? ResponseUtil.buildNotFoundResponse("No hay tareas completadas")
	            : ResponseUtil.buildSuccessResponse(tasks);
	}
	
	@GetMapping("/completed/{id}")
	public ResponseEntity<APIResponse<List<Task>>> getCompletedTasksByProyectId(@PathVariable("id") Integer id) {
	    List<Task> tasks = service.findCompletedByProjectId(id);
	    return tasks.isEmpty() ? ResponseUtil.buildNotFoundResponse("No hay tareas completadas")
	            : ResponseUtil.buildSuccessResponse(tasks);
	}	
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Task>> getTaskById(@PathVariable("id") Integer id){
		Task task = service.findById(id);
		return task == null ? ResponseUtil.buildNotFoundResponse("No se encontró la tarea con el identificador proporcionado")
				: ResponseUtil.buildSuccessResponse(task);	
	}	
	
    @PostMapping
    public ResponseEntity<APIResponse<Task>> createTask(@RequestBody Task task, BindingResult result) {
        if(exists(task.getId())) {
        	return ResponseUtil.buildBadRequestResponse("Ya existe una tarea con el identificador proporcionado");
        }else {
        	service.save(task);
        	return ResponseUtil.buildCreatedResponse(task);
        }    	
    }	

	@PutMapping
	public ResponseEntity<APIResponse<Task>> modifyTask(@RequestBody Task task) {
		if(exists(task.getId())) {
			service.save(task);
        	return ResponseUtil.buildSuccessResponse(task);		
        }else {
			return ResponseUtil.buildBadRequestResponse("No existe la tarea con el identificador proporcionado");
		}
	}
	
	@PutMapping("/complete/{id}")
	public ResponseEntity<APIResponse<Task>> completeTask(@PathVariable("id") Integer id) {
		if(exists(id)) {
			Task task = service.findById(id);
			task.setCompleted(true);
			service.save(task);
        	return ResponseUtil.buildSuccessResponse(task);		
        }else {
			return ResponseUtil.buildBadRequestResponse("No existe la tarea con el identificador proporcionado");
		}
	}	
	
	@PutMapping("/uncomplete/{id}")
	public ResponseEntity<APIResponse<Task>> uncompleteTask(@PathVariable("id") Integer id) {
		if(exists(id)) {
			Task task = service.findById(id);
			task.setCompleted(false);
			service.save(task);
        	return ResponseUtil.buildSuccessResponse(task);		
        }else {
			return ResponseUtil.buildBadRequestResponse("No existe la tarea con el identificador proporcionado");
		}
	}	
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Task>> deleteTask(@PathVariable("id") Integer id){
		if(exists(id)) {
			service.delete(id);
        	return ResponseUtil.buildCreatedResponse(null);		
		}else {
			return ResponseUtil.buildBadRequestResponse("No existe la tarea con el identificador proporcionado");
		}
	}
	
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
    	List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        APIResponse<Task> response = new APIResponse<Task>(HttpStatus.BAD_REQUEST.value(), errors, null);
        return ResponseEntity.badRequest().body(response);
    }
    
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<APIResponse<Task>> handleEntityNotFoundException(EntityNotFoundException ex) {
    	
    	return ResponseUtil.buildNotFoundResponse(ex.getMessage());
    }
    
    
    private boolean exists(Integer id) {
    	return id != null && service.findById(id) != null;
    }

}
