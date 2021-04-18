package pl.merdala.cleantimetracker.projectcontext.domain.usecase.listtasks;

import pl.merdala.cleantimetracker.annotation.UseCase;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.QueryTasksPort;

import java.util.List;

@UseCase
public class ListTasksUseCase {

    private final QueryTasksPort queryTasksPort;

    public ListTasksUseCase(QueryTasksPort queryTasksPort) {
        this.queryTasksPort = queryTasksPort;
    }

    public List<Task> listTasksForProjects(ProjectId projectId) {
        return queryTasksPort.listTasksForProject(projectId);
    }
}
