package pl.merdala.cleantimetracker.projectcontext.domain.usecase.changetaskstatus;

import pl.merdala.cleantimetracker.annotation.UseCase;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskId;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskStatus;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.QueryTasksPort;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.UpdateTaskPort;
import pl.merdala.cleantimetracker.projectcontext.domain.usecase.TaskNotFoundException;

import javax.transaction.Transactional;

@UseCase
@Transactional
public class ChangeTaskStatusUseCase {

    private final UpdateTaskPort updateTaskPort;

    private final QueryTasksPort queryTasksPort;

    public ChangeTaskStatusUseCase(UpdateTaskPort updateTaskPort, QueryTasksPort queryTasksPort) {
        this.updateTaskPort = updateTaskPort;
        this.queryTasksPort = queryTasksPort;
    }

    public void activateTask(TaskId taskId){
        changeTaskStatus(taskId, TaskStatus.ACTIVE);
    }

    public void deactivateTask(TaskId taskId){
        changeTaskStatus(taskId,TaskStatus.INACTIVE);
    }

    private void changeTaskStatus(TaskId taskId, TaskStatus status) {
        Task task = queryTasksPort.findOne(taskId).orElseThrow(()->new TaskNotFoundException(taskId));
        updateTaskPort.changeStatus(task,status);
    }

}
