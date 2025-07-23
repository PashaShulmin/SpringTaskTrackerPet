package ru.pavel.task_tracker_spring.enums;

public enum TaskStatus {
    TODO,
    IN_PROGRESS,
    DONE;

    public String getStatus() {
        return this.name();
    }
}
