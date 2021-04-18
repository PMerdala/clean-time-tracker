package pl.merdala.cleantimetracker.projectcontext.adapter.in.web.list;

import org.springframework.stereotype.Component;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProjectListModelMapper {

    ProjectListModel toModel(Project project){
        return ProjectListModel.builder()
                .id(project.getId().getValue())
                .name(project.getName())
                .status(project.getStatus())
                .build();
    }

    List<ProjectListModel> toModels(List<Project> projects){
        return projects.stream().map(this::toModel).collect(Collectors.toList());
    }
}
