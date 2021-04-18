package pl.merdala.cleantimetracker.timecontext.adapter.out.projectcontext;

import pl.merdala.cleantimetracker.annotation.Mapper;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskStatus;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeTrackingTask;

import java.util.List;
import java.util.stream.Collectors;

@Mapper
public class TaskMapper {
    TimeTrackingTask toTimeTrackingTask(Task task) {
        return TimeTrackingTask.builder()
                .projectName(task.getProject().getName())
                .name(task.getName())
                .invoiceable(task.isInvoiceable())
                .id(task.getId().getValue())
                .active(task.getStatus() == TaskStatus.ACTIVE)
                .projectId(task.getProject().getId().getValue())
                .build();
    }

    List<TimeTrackingTask> toTimeTrackingTasks(List<Task> tasks) {
        return tasks.stream().map(task -> toTimeTrackingTask(task)).collect(Collectors.toList());
    }
}
