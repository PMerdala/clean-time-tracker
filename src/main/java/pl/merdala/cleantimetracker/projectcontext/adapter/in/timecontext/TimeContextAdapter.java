package pl.merdala.cleantimetracker.projectcontext.adapter.in.timecontext;

import pl.merdala.cleantimetracker.annotation.ContextAdapter;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskId;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.QueryTasksPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ContextAdapter
public class TimeContextAdapter {

    private final QueryTasksPort queryTasksPort;

    public TimeContextAdapter(QueryTasksPort queryTasksPort) {
        this.queryTasksPort = queryTasksPort;
    }

    public List<Task> listTasksByIds(List<Long> taskIds){
        return queryTasksPort.listByIds(taskIds.stream().map(TaskId::of).collect(Collectors.toList()));
    }

    public Optional<Task> loadTask(Long taskId){
        return queryTasksPort.findOne(TaskId.of(taskId));
    }

    public List<Task> listAll(){
        return queryTasksPort.listAllTasks();
    }
}
