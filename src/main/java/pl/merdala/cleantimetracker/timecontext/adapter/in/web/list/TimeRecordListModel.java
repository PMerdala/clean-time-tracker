package pl.merdala.cleantimetracker.timecontext.adapter.in.web.list;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TimeRecordListModel {
    private LocalDate date;
    private Float hours;
    private String taskName;
}
