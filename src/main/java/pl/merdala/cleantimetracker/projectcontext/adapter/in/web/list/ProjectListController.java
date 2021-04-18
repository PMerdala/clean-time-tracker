package pl.merdala.cleantimetracker.projectcontext.adapter.in.web.list;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.QueryProjectsPort;
import pl.merdala.cleantimetracker.projectcontext.domain.usecase.changeprojectstatus.ChangeProjectStatusUseCase;

import java.util.List;

@Controller
public class ProjectListController {

    private final QueryProjectsPort queryProjectsPort;

    private final ProjectListModelMapper projectListModelMapper;

    private final ChangeProjectStatusUseCase changeProjectStatusUseCase;

    public ProjectListController(QueryProjectsPort queryProjectsPort,
                                 ProjectListModelMapper projectListModelMapper,
                                 ChangeProjectStatusUseCase changeProjectStatusUseCase) {
        this.queryProjectsPort = queryProjectsPort;
        this.projectListModelMapper = projectListModelMapper;
        this.changeProjectStatusUseCase = changeProjectStatusUseCase;
    }

    @GetMapping(path = {"/projects", "/"})
    String displayProjectList(Model model) {
        List<Project> projects = queryProjectsPort.listProjects();
        model.addAttribute("projects", projectListModelMapper.toModels(projects));
        return "projects/listProjects.html";
    }

    @PostMapping("/projects/{id}/activate")
    String activateProject(@PathVariable("id") Long projectId) {
        changeProjectStatusUseCase.activateProject(ProjectId.of(projectId));
        return "redirect:/projects";
    }

    @PostMapping("/projects/{id}/deactivate")
    String deactivateProject(@PathVariable("id") Long projectId) {
        changeProjectStatusUseCase.deactivateProject(ProjectId.of(projectId));
        return "redirect:/projects";
    }
}
