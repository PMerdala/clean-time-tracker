package pl.merdala.cleantimetracker.timecontext.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeRecord {

    private TimeRecordId id;

    private Long taskId;

    private LocalDate date;

    private Integer minutes;

    private TimeRecordStatus status;

}
