package pl.merdala.cleantimetracker.projectcontext.adapter.in.web.edit;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
class TaskModel {

    private Long id;

    private String name;

    private Boolean invoiceable;

    private TaskStatus status;

    public boolean isActive() {
        return status == TaskStatus.ACTIVE;
    }
}
