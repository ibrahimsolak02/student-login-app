package com.ibrahimsolak.studentloginapp.exception;

public class EntityNotFoundException extends RuntimeException {

    public EntityNotFoundException(Long id, Class<?> entity) {
        super("The" + entity.getName().toLowerCase() + "with id" + id + "was not found");
    }

    public EntityNotFoundException(String username, Class<?> entity) {
        super("The" + entity.getName().toLowerCase() + "with username" + username + "was not found");
    }
}
