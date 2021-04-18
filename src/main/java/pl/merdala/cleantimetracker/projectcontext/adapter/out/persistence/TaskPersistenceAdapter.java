package pl.merdala.cleantimetracker.projectcontext.adapter.out.persistence;

import pl.merdala.cleantimetracker.annotation.PersistenceAdapter;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskId;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskStatus;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.CreateTaskPort;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.QueryTasksPort;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.UpdateTaskPort;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceAdapter
public class TaskPersistenceAdapter implements CreateTaskPort, QueryTasksPort, UpdateTaskPort {

    private final TaskEntityRepository taskEntityRepository;
    private final TaskEntityMapper taskEntityMapper;

    public TaskPersistenceAdapter(TaskEntityRepository taskEntityRepository, TaskEntityMapper taskEntityMapper) {
        this.taskEntityRepository = taskEntityRepository;
        this.taskEntityMapper = taskEntityMapper;
    }

    @Override
    public Task saveTask(Task task) {
        TaskEntity taskEntity = taskEntityMapper.toEntity(task);
        TaskEntity savedTaskEntity = taskEntityRepository.save(taskEntity);
        return taskEntityMapper.toDomainObject(savedTaskEntity);
    }

    @Override
    public void changeStatus(Task task, TaskStatus status) {
        taskEntityRepository.updateStatus(task.getId().getValue(), status);
    }

    @Override
    public List<Task> listTasksForProject(ProjectId projectId) {
        List<TaskEntity> taskEntities = taskEntityRepository.findByProjectId(projectId.getValue());
        return taskEntityMapper.toDomainObjects(taskEntities);
    }

    @Override
    public Optional<Task> findOne(TaskId taskId) {
        return taskEntityRepository.findById(taskId.getValue()).map(taskEntityMapper::toDomainObject);
    }

    @Override
    public List<Task> listByIds(List<TaskId> taskIds) {
        List<TaskEntity> taskEntities = taskEntityRepository
                .findByIdIn(taskIds.stream()
                        .map(TaskId::getValue)
                        .collect(Collectors.toList()));
        return taskEntityMapper.toDomainObjects(taskEntities);
    }

    @Override
    public List<Task> listAllTasks() {
        return taskEntityMapper.toDomainObjects(taskEntityRepository.findAll());
    }
}
