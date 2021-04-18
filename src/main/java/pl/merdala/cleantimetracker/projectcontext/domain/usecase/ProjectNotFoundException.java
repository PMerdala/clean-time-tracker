package pl.merdala.cleantimetracker.projectcontext.domain.usecase;

import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;

public class ProjectNotFoundException extends RuntimeException {

    public ProjectNotFoundException(ProjectId projectId) {
        super(String.format("Project with ID %d not found!", projectId.getValue()));
    }
}
