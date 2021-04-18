package pl.merdala.cleantimetracker.timecontext.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecordStatus;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "TIME_RECORD")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeRecordEntity {
    @Id
    @GeneratedValue
    private Long id;

    @Column(name = "DATE")
    private LocalDate date;

    @Column(name = "MINUTES")
    private Integer minutes;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private TimeRecordStatus status;

    @Column(name = "TASK_ID")
    private Long taskId;
}
