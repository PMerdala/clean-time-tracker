package pl.merdala.cleantimetracker.projectcontext.domain.usecase.listprojects;

import pl.merdala.cleantimetracker.annotation.UseCase;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.QueryProjectsPort;

import java.util.List;

@UseCase
public class ListProjectUseCase {

    private final QueryProjectsPort queryProjectsPort;

    public ListProjectUseCase(QueryProjectsPort queryProjectsPort) {
        this.queryProjectsPort = queryProjectsPort;
    }

    public List<Project> listProjects(){
        return queryProjectsPort.listProjects();
    }
}
