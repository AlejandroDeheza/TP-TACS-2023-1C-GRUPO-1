package com.tacs.backend.exception;

import com.mongodb.MongoException;

public class EntityNotFoundException extends RuntimeException {
    public EntityNotFoundException(String msg) {
        super(msg);
    }
}
