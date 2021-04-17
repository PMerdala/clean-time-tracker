package pl.merdala.cleantimetracker.timecontext.domain.port.out;

import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeTrackingTask;

import java.util.List;
import java.util.Set;

public interface QueryTasksPort {

    List<TimeTrackingTask> listByIds(Set<Long> taskIds);

    TimeTrackingTask loadTask(Long taskId);

    List<TimeTrackingTask> listAllTasks();
}
