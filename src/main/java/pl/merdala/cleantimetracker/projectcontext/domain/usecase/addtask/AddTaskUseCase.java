package pl.merdala.cleantimetracker.projectcontext.domain.usecase.addtask;

import pl.merdala.cleantimetracker.annotation.UseCase;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.CreateTaskPort;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.QueryProjectsPort;
import pl.merdala.cleantimetracker.projectcontext.domain.usecase.ProjectNotFoundException;

import javax.transaction.Transactional;

@UseCase
@Transactional
public class AddTaskUseCase {

    private final QueryProjectsPort queryProjectsPort;
    private final CreateTaskPort createTaskPort;

    public AddTaskUseCase(QueryProjectsPort queryProjectsPort, CreateTaskPort createTaskPort) {
        this.queryProjectsPort = queryProjectsPort;
        this.createTaskPort = createTaskPort;
    }

    public void addTask(String taskName,boolean invoiceable, ProjectId projectId){
        Project project = findProjectOrFail(projectId);
        Task task = Task.builder()
                .name(taskName)
                .invoiceable(invoiceable)
                .project(project)
                .build();
        createTaskPort.saveTask(task);
    }

    private Project findProjectOrFail(ProjectId projectId) {
        return queryProjectsPort.findOne(projectId).orElseThrow(() -> new ProjectNotFoundException(projectId));
    }
}
