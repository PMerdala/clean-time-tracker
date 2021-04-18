package pl.merdala.cleantimetracker.projectcontext.domain.usecase.loadproject;

import pl.merdala.cleantimetracker.annotation.UseCase;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.QueryProjectsPort;
import pl.merdala.cleantimetracker.projectcontext.domain.usecase.ProjectNotFoundException;

@UseCase
public class LoadProjectUseCase {

    private final QueryProjectsPort queryProjectsPort;

    public LoadProjectUseCase(QueryProjectsPort queryProjectsPort) {
        this.queryProjectsPort = queryProjectsPort;
    }

    public Project loadProject(ProjectId projectId) {
        return queryProjectsPort.findOne(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
    }
}
