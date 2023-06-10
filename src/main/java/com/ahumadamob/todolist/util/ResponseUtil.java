package com.ahumadamob.todolist.util;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.ahumadamob.todolist.controller.APIResponse;

public class ResponseUtil {

    private ResponseUtil() {
        // Constructor privado para evitar instanciación
    }

    public static <T> ResponseEntity<APIResponse<T>> buildSuccessResponse(T data) {
        APIResponse<T> response = new APIResponse<>(HttpStatus.OK.value(), null, data);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
    
    public static <T> ResponseEntity<APIResponse<T>> buildCreatedResponse(T data) {
        APIResponse<T> response = new APIResponse<>(HttpStatus.CREATED.value(), null, data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    } 

    public static <T> ResponseEntity<APIResponse<T>> buildErrorResponse(HttpStatus status, String message) {
        APIResponse<T> response = new APIResponse<>(status.value(), addSingleMessage(message), null);
        return ResponseEntity.status(status).body(response);
    }    

    public static <T> ResponseEntity<APIResponse<T>> buildNotFoundResponse(String message) {
        APIResponse<T> response = new APIResponse<>(HttpStatus.NOT_FOUND.value(), addSingleMessage(message), null);
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }    

    public static <T> ResponseEntity<APIResponse<T>> buildBadRequestResponse(String message) {
        APIResponse<T> response = new APIResponse<>(HttpStatus.BAD_REQUEST.value(), addSingleMessage(message), null);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    } 
    
    private static List<String> addSingleMessage(String message) {
        List<String> messages = new ArrayList<>();
        messages.add(message);
        return messages;
    }
}

