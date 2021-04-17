package pl.merdala.cleantimetracker.timecontext.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TimeTrackingTask {

    private Long id;

    private String name;

    private Long projectId;

    private String projectName;

    private Boolean active;

    private Boolean invoiceAble;
}
