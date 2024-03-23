package org.demo.error;

public class TeamNotFoundException extends RuntimeException{
    public TeamNotFoundException(String message) {
        super(message);
    }
}
