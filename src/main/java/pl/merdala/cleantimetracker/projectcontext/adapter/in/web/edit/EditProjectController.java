package pl.merdala.cleantimetracker.projectcontext.adapter.in.web.edit;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Task;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskId;
import pl.merdala.cleantimetracker.projectcontext.domain.usecase.addtask.AddTaskUseCase;
import pl.merdala.cleantimetracker.projectcontext.domain.usecase.changetaskstatus.ChangeTaskStatusUseCase;
import pl.merdala.cleantimetracker.projectcontext.domain.usecase.listtasks.ListTasksUseCase;
import pl.merdala.cleantimetracker.projectcontext.domain.usecase.loadproject.LoadProjectUseCase;

import java.util.List;

@Controller
class EditProjectController {

    private final ListTasksUseCase listTasksUseCase;

    private final LoadProjectUseCase loadProjectUseCase;

    private final EditProjectModelMapper editProjectModelMapper;

    private final AddTaskUseCase addTaskUseCase;

    private final ChangeTaskStatusUseCase changeTaskStatusUseCase;

    EditProjectController(ListTasksUseCase listTasksUseCase,
                          LoadProjectUseCase loadProjectUseCase,
                          EditProjectModelMapper editProjectModelMapper,
                          AddTaskUseCase addTaskUseCase,
                          ChangeTaskStatusUseCase changeTaskStatusUseCase) {
        this.listTasksUseCase = listTasksUseCase;
        this.loadProjectUseCase = loadProjectUseCase;
        this.editProjectModelMapper = editProjectModelMapper;
        this.addTaskUseCase = addTaskUseCase;
        this.changeTaskStatusUseCase = changeTaskStatusUseCase;
    }

    @GetMapping("/projects/{id}")
    String displayProjectForm(@PathVariable("id") Long projectId, Model model) {
        ProjectId projectIdToLoad = ProjectId.of(projectId);
        Project project = loadProjectUseCase.loadProject(projectIdToLoad);
        List<Task> tasks = listTasksUseCase.listTasksForProjects(projectIdToLoad);
        EditProjectModel editProjectModel = editProjectModelMapper.toModel(project, tasks);
        model.addAttribute("project", editProjectModel);
        model.addAttribute("addTaskForm", new AddTaskForm());
        return "project/editProject.html";
    }

    @PostMapping("/projects/{id}/add-task")
    String addTask(@PathVariable("id") Long projectId,
                   @ModelAttribute("addTaskForm") AddTaskForm addTaskForm) {
        addTaskUseCase.addTask(addTaskForm.getName(), addTaskForm.isInvoiceable(), ProjectId.of(projectId));
        return "redirect:/projects/{id}";
    }

    @PostMapping("/projects/{projectId}/tasks/{taskId}/activate")
    String activateTask(@PathVariable("taskId") Long taskId) {
        changeTaskStatusUseCase.activateTask(TaskId.of(taskId));
        return "redirect:/projects/{projectId}";
    }

    @PostMapping("/projects/{projectId}/tasks/{taskId}/deactivate")
    String deactivateTask(@PathVariable("taskId") Long taskId) {
        changeTaskStatusUseCase.deactivateTask(TaskId.of(taskId));
        return "redirect:/projects/{projectId}";
    }
}
