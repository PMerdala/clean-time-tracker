package pl.merdala.cleantimetracker.projectcontext.adapter.in.web.edit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectStatus;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class EditProjectModel {

    private Long id;

    private String name;

    private ProjectStatus status;

    private List<TaskModel> tasks;

    public boolean isActive() {
        return status == ProjectStatus.ACTIVE;
    }
}
