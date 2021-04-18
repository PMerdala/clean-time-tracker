package pl.merdala.cleantimetracker.projectcontext.adapter.out.persistence;

import pl.merdala.cleantimetracker.annotation.PersistenceAdapter;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectStatus;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.CreateProjectPort;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.QueryProjectsPort;
import pl.merdala.cleantimetracker.projectcontext.domain.port.out.persistence.UpdateProjectPort;

import java.util.List;
import java.util.Optional;

@PersistenceAdapter
class ProjectPersistenceAdapter  implements CreateProjectPort, QueryProjectsPort, UpdateProjectPort {

    private final ProjectRepository projectRepository;

    private final ProjectEntityMapper projectEntityMapper;

    public ProjectPersistenceAdapter(ProjectRepository projectRepository, ProjectEntityMapper projectEntityMapper) {
        this.projectRepository = projectRepository;
        this.projectEntityMapper = projectEntityMapper;
    }

    @Override
    public Project createProject(Project project) {
        ProjectEntity entity = projectEntityMapper.toEntity(project);
        ProjectEntity savedEntity = projectRepository.save(entity);
        return projectEntityMapper.toDomainObject(savedEntity);
    }

    @Override
    public List<Project> listProjects() {
        Iterable<ProjectEntity> entities = projectRepository.findAll();
        return projectEntityMapper.toDomainObjects(entities);
    }

    @Override
    public Optional<Project> findOne(ProjectId projectId) {
        Optional<ProjectEntity> optionalProjectEntity = projectRepository.findById(projectId.getValue());
        return optionalProjectEntity.map(projectEntityMapper::toDomainObject);
    }

    @Override
    public void changeStatus(Project project, ProjectStatus status) {
        projectRepository.updateStatus(project.getId().getValue(),status);
    }
}
