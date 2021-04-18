package pl.merdala.cleantimetracker.projectcontext.adapter.in.web.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectStatus;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectListModel {

    private Long id;

    private String name;

    private ProjectStatus status;

    public boolean isActive() {
        return status == ProjectStatus.ACTIVE;
    }
}
