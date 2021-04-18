package pl.merdala.cleantimetracker.projectcontext.domain.usecase;

import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskId;

public class TaskNotFoundException extends RuntimeException {

    public TaskNotFoundException(TaskId taskId) {
        super(String.format("Task with ID %d not found!", taskId.getValue()));
    }
}
