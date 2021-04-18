package pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence;

import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectStatus;

public interface UpdateProjectPort {

    void changeStatus(Project project, ProjectStatus status);
}
