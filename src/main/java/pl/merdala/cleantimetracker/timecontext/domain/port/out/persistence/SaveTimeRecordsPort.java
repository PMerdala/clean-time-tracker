package pl.merdala.cleantimetracker.timecontext.domain.port.out.persistence;

import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecord;

import java.util.List;

public interface SaveTimeRecordsPort {

    void saveTimeRecords(List<TimeRecord> timeRecord);
}
