package pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence;

import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskId;

import java.util.List;
import java.util.Optional;

public interface QueryTasksPort {

    List<Task> listTasksForProject(ProjectId projectId);

    Optional<Task> findOne(TaskId taskId);

    List<Task> listByIds(List<TaskId> taskIds);

    List<Task> listAllTasks();
}
