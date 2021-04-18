package pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence;

import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;

public interface CreateTaskPort {

    Task saveTask(Task task);
}
