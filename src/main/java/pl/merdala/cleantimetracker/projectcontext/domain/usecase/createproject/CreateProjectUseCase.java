package pl.merdala.cleantimetracker.projectcontext.domain.usecase.createproject;

import pl.merdala.cleantimetracker.annotation.UseCase;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectStatus;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.CreateProjectPort;

import javax.transaction.Transactional;

@UseCase
@Transactional
public class CreateProjectUseCase {

    private final CreateProjectPort createProjectPort;

    public CreateProjectUseCase(CreateProjectPort createProjectPort) {
        this.createProjectPort = createProjectPort;
    }

    public Project createProject(String projectName){
        Project project = Project.builder()
                .name(projectName)
                .status(ProjectStatus.INACTIVE)
                .build();
        return createProjectPort.createProject(project);
    }
}
