package pl.merdala.cleantimetracker.projectcontext.adapter.out.persistence;

import pl.merdala.cleantimetracker.annotation.Mapper;
import pl.merdala.cleantimetracker.mapper.EntityMapper;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskId;

import javax.persistence.EntityManager;
import java.util.Optional;

@Mapper
public class TaskEntityMapper implements EntityMapper<TaskEntity, Task> {

    private final EntityManager entityManager;
    private final ProjectEntityMapper projectEntityMapper;

    public TaskEntityMapper(EntityManager entityManager, ProjectEntityMapper projectEntityMapper) {
        this.entityManager = entityManager;
        this.projectEntityMapper = projectEntityMapper;
    }

    @Override
    public TaskEntity toEntity(Task task) {
        return TaskEntity.builder()
                .id(Optional.of(task.getId()).map(TaskId::getValue).orElse(null))
                .name(task.getName())
                .invoiceable(task.isInvoiceable())
                .project(projectReference(task.getProject().getId()))
                .status(task.getStatus())
                .build();
    }

    @Override
    public Task toDomainObject(TaskEntity taskEntity) {
        return Task.builder()
                .id(TaskId.of(taskEntity.getId()))
                .name(taskEntity.getName())
                .invoiceable(taskEntity.getInvoiceable())
                .project(projectEntityMapper.toDomainObject(taskEntity.getProject()))
                .status(taskEntity.getStatus())
                .build();
    }

    private ProjectEntity projectReference(ProjectId id) {
        if (id==null||id.getValue()==null){
            return null;
        }
        return entityManager.getReference(ProjectEntity.class,id.getValue());
    }

}
