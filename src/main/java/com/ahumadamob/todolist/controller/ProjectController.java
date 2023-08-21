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
import com.ahumadamob.todolist.service.IProjectService;
import com.ahumadamob.todolist.util.ResponseUtil;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@RestController
@RequestMapping("/api/v1/project")
public class ProjectController {
	
	@Autowired
	private IProjectService service;
	
	@GetMapping("/all")
	public ResponseEntity<APIResponse<List<Project>>> getAllProjects(){
	    List<Project> project = service.findAll();
	    return project.isEmpty() ? ResponseUtil.buildNotFoundResponse("No hay proyectos")
	            : ResponseUtil.buildSuccessResponse(project);
	};	
	
	@GetMapping("{id}")
	public ResponseEntity<APIResponse<Project>> getProjectById(@PathVariable("id") Integer id){
		Project project = service.findById(id);
		return project == null ? ResponseUtil.buildNotFoundResponse("No se encontró el proyecto con el identificador proporcionado")
				: ResponseUtil.buildSuccessResponse(project);	
	};	
	
    @PostMapping
    public ResponseEntity<APIResponse<Project>> createProject(@RequestBody Project project, BindingResult result) {
        if(exists(project.getId())) {
        	return ResponseUtil.buildBadRequestResponse("Ya existe un proyecto con el identificador proporcionado");
        }else {
        	service.save(project);
        	return ResponseUtil.buildCreatedResponse(project);
        }  
    }	

	@PutMapping
	public ResponseEntity<APIResponse<Project>> modifyProject(@RequestBody Project project) {
		if(exists(project.getId())) {
			service.save(project);
        	return ResponseUtil.buildSuccessResponse(project);		
        }else {
			return ResponseUtil.buildBadRequestResponse("No existe el projecto con el identificador proporcionado");
		}
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<APIResponse<Project>> deleteProject(@PathVariable("id") Integer id){
		if(exists(id)) {
			service.delete(id);
        	return ResponseUtil.buildCreatedResponse(null);		
		}else {
			return ResponseUtil.buildBadRequestResponse("No existe lel proyecto con el identificador proporcionado");
		}
	}
	
    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<APIResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
    	List<String> errors = new ArrayList<>();
        for (ConstraintViolation<?> violation : ex.getConstraintViolations()) {
            errors.add(violation.getMessage());
        }
        APIResponse<Project> response = new APIResponse<Project>(HttpStatus.BAD_REQUEST.value(), errors, null);
        return ResponseEntity.badRequest().body(response);
    }
    
     private boolean exists(Integer id) {
    	 return id != null && service.findById(id) != null;
    }

}
