package pl.merdala.cleantimetracker.projectcontext.adapter.out.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectStatus;

interface ProjectRepository extends CrudRepository<ProjectEntity, Long> {

    @Query("update #{#entityName} p set p.status=:status where p.id = :id")
    @Modifying
    int updateStatus(@Param("id") Long projectId, @Param("status") ProjectStatus projectStatus);

}
