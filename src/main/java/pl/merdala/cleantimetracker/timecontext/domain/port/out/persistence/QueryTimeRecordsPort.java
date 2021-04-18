package pl.merdala.cleantimetracker.timecontext.domain.port.out.persistence;

import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecord;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface QueryTimeRecordsPort {

    List<TimeRecord> listByDates(Set<LocalDate> dates);

    List<TimeRecord> listTimeRecords(LocalDate from, LocalDate until);
}
