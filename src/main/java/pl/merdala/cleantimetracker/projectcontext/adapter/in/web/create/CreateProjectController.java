package pl.merdala.cleantimetracker.projectcontext.adapter.in.web.create;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import pl.merdala.cleantimetracker.projectcontext.domain.usecase.createproject.CreateProjectUseCase;

@Controller
public class CreateProjectController {

    private final CreateProjectUseCase createProjectUseCase;

    public CreateProjectController(CreateProjectUseCase createProjectUseCase) {
        this.createProjectUseCase = createProjectUseCase;
    }

    @GetMapping("/projects/create")
    String displayCreateProjectForm(Model model) {
        model.addAttribute("project", new CreateProjectForm());
        return "project/createProject.html";
    }

    @PostMapping("/projects")
    String createProject(@ModelAttribute("project") CreateProjectForm createProjectForm) {
        createProjectUseCase.createProject(createProjectForm.getName());
        return "redirect:/projects";
    }
}
