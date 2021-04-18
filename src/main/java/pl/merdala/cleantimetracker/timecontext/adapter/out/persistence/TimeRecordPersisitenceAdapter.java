package pl.merdala.cleantimetracker.timecontext.adapter.out.persistence;

import pl.merdala.cleantimetracker.annotation.PersistenceAdapter;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecord;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.persistence.QueryTimeRecordsPort;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.persistence.SaveTimeRecordsPort;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@PersistenceAdapter
public class TimeRecordPersisitenceAdapter implements QueryTimeRecordsPort, SaveTimeRecordsPort {

    private final TimeRecordMapper timeRecordMapper;

    private final TimeRecordRepository timeRecordRepository;

    public TimeRecordPersisitenceAdapter(TimeRecordMapper timeRecordMapper, TimeRecordRepository timeRecordRepository) {
        this.timeRecordMapper = timeRecordMapper;
        this.timeRecordRepository = timeRecordRepository;
    }

    @Override
    public List<TimeRecord> listByDates(Set<LocalDate> dates) {
        List<TimeRecordEntity> recordEntities = timeRecordRepository.findByDateIn(dates);
        return timeRecordMapper.toDomainObjects(recordEntities);
    }

    @Override
    public List<TimeRecord> listTimeRecords(LocalDate from, LocalDate until) {
        List<TimeRecordEntity> recordEntities = timeRecordRepository.findByDateBetween(from, until);
        return timeRecordMapper.toDomainObjects(recordEntities);
    }

    @Override
    public void saveTimeRecords(List<TimeRecord> records) {
        List<TimeRecordEntity> recordEntities = timeRecordMapper.toEntities(records);
        timeRecordRepository.saveAll(recordEntities);
    }
}
