package pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence;

import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskStatus;

public interface UpdateTaskPort {

    void changeStatus(Task task, TaskStatus status);
}
