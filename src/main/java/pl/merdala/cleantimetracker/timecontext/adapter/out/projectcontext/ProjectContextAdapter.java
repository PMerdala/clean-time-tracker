package pl.merdala.cleantimetracker.timecontext.adapter.out.projectcontext;

import pl.merdala.cleantimetracker.annotation.ContextAdapter;
import pl.merdala.cleantimetracker.projectcontext.adapter.in.timecontext.TimeContextAdapter;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeTrackingTask;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.QueryTasksPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@ContextAdapter
public class ProjectContextAdapter implements QueryTasksPort {
    private final TimeContextAdapter timeContextAdapter;
    private final TaskMapper taskMapper;

    public ProjectContextAdapter(TimeContextAdapter timeContextAdapter, TaskMapper taskMapper) {
        this.timeContextAdapter = timeContextAdapter;
        this.taskMapper = taskMapper;
    }


    @Override
    public List<TimeTrackingTask> listByIds(Set<Long> taskIds) {
        List<Task> tasks = timeContextAdapter.listTasksByIds(new ArrayList<>(taskIds));
        return taskMapper.toTimeTrackingTasks(tasks);
    }

    @Override
    public TimeTrackingTask loadTask(Long taskId) {
        return timeContextAdapter.loadTask(taskId).map(taskMapper::toTimeTrackingTask).orElse(null);
    }

    @Override
    public List<TimeTrackingTask> listAllTasks() {
        return timeContextAdapter.listAll().stream().map(taskMapper::toTimeTrackingTask).collect(Collectors.toList());
    }
}
