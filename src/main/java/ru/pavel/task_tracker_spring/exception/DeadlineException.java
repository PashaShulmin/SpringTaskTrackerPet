package ru.pavel.task_tracker_spring.exception;

public class DeadlineException extends Throwable {
    public DeadlineException(String message) {
        super(message);
    }
}
