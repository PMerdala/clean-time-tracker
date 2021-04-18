package pl.merdala.cleantimetracker.projectcontext.adapter.in.web.edit;

import org.springframework.stereotype.Component;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;

import java.util.List;

@Component
public class EditProjectModelMapper {
    private final TaskModelMapper taskModelMapper;

    public EditProjectModelMapper(TaskModelMapper taskModelMapper) {
        this.taskModelMapper = taskModelMapper;
    }

    EditProjectModel toModel(Project domainObject, List<Task> tasks) {
        return EditProjectModel.builder()
                .id(domainObject.getId().getValue())
                .name(domainObject.getName())
                .status(domainObject.getStatus())
                .tasks(taskModelMapper.toModels(tasks))
                .build();
    }

}
