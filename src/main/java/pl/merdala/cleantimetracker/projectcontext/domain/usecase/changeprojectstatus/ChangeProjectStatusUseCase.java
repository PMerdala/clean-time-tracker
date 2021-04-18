package pl.merdala.cleantimetracker.projectcontext.domain.usecase.changeprojectstatus;

import pl.merdala.cleantimetracker.annotation.UseCase;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectStatus;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.QueryProjectsPort;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.UpdateProjectPort;
import pl.merdala.cleantimetracker.projectcontext.domain.usecase.ProjectNotFoundException;

import javax.transaction.Transactional;
import java.util.Optional;

@UseCase
@Transactional
public class ChangeProjectStatusUseCase {

    private final UpdateProjectPort updateProjectPort;

    private final QueryProjectsPort queryProjectsPort;

    public ChangeProjectStatusUseCase(UpdateProjectPort updateProjectPort, QueryProjectsPort queryProjectsPort) {
        this.updateProjectPort = updateProjectPort;
        this.queryProjectsPort = queryProjectsPort;
    }

    public void activateProject(ProjectId projectId) {
        changeStatusProject(projectId, ProjectStatus.ACTIVE);
    }

    public void deactivateProject(ProjectId projectId) {
        changeStatusProject(projectId, ProjectStatus.INACTIVE);
    }


    private void changeStatusProject(ProjectId projectId, ProjectStatus status) {
        Optional<Project> project = queryProjectsPort.findOne(projectId);
        updateProjectPort.changeStatus(
                project.orElseThrow(() -> new ProjectNotFoundException(projectId)),
                status);
    }
}
