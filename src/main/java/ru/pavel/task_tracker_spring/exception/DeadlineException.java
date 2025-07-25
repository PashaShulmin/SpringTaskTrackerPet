package ru.pavel.task_tracker_spring.exception;

public class DeadlineException extends RuntimeException {
    public DeadlineException(String message) {
        super(message);
    }
}
