package org.demo.error;

public class InvalidTeamException extends RuntimeException{
    public InvalidTeamException(String message) {
        super(message);
    }
}
