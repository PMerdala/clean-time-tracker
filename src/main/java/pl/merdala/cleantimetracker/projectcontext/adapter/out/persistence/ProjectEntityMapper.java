package pl.merdala.cleantimetracker.projectcontext.adapter.out.persistence;

import pl.merdala.cleantimetracker.annotation.Mapper;
import pl.merdala.cleantimetracker.mapper.EntityMapper;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.Project;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectId;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Mapper
class ProjectEntityMapper implements EntityMapper<ProjectEntity,Project> {

    @Override
    public ProjectEntity toEntity(Project domainObject) {
        return ProjectEntity.builder()
                .id(Optional.ofNullable(domainObject.getId()).map(ProjectId::getValue).orElse(null))
                .name(domainObject.getName())
                .status(domainObject.getStatus())
                .build();
    }

    @Override
    public Project toDomainObject(ProjectEntity entity){
        return Project.builder()
                .id(ProjectId.of(entity.getId()))
                .name(entity.getName())
                .status(entity.getStatus())
                .build();
    }
}
