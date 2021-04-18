package pl.merdala.cleantimetracker.projectcontext.adapter.out.persistence;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskStatus;

import java.util.List;

public interface TaskEntityRepository extends CrudRepository<TaskEntity,Long> {

    List<TaskEntity> findByProjectId(long projectId);

    @Query("update #{#entityName} t set t.status = :status where t.id = :id")
    @Modifying
    int updateStatus(@Param("id") Long taskId, @Param("status") TaskStatus taskStatus);

    List<TaskEntity> findByIdIn(List<Long> ids);
}
