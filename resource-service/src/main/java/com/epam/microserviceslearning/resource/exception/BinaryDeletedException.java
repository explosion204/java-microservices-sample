package com.epam.microserviceslearning.resource.exception;

public class BinaryDeletedException extends BinaryNotFoundException {
    public BinaryDeletedException(long id) {
        super(id);
    }
}
