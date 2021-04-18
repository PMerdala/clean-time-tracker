package pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence;

import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;

public interface CreateProjectPort {

    /**
     * Persists a new project
     * @param project to persist
     * @return persist Project
     */
    Project createProject(Project project);
}
