package com.springapi.orders.domain.exception;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String resource, Object id){
      super(resource + " não encontrado com ID: " + id);
    }
}
