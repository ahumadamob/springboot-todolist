package com.ahumadamob.todolist.exceptions;

public class DataNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public DataNotFoundException(String message) {
        super(message);
    }
}
