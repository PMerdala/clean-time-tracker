package pl.merdala.cleantimetracker.timecontext.adapter.out.persistence;

import pl.merdala.cleantimetracker.annotation.Mapper;
import pl.merdala.cleantimetracker.mapper.EntityMapper;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecord;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecordId;

@Mapper
public class TimeRecordMapper implements EntityMapper<TimeRecordEntity, TimeRecord> {

    @Override
    public TimeRecord toDomainObject(TimeRecordEntity entity) {
        return TimeRecord.builder()
                .status(entity.getStatus())
                .minutes(entity.getMinutes())
                .date(entity.getDate())
                .taskId(entity.getTaskId())
                .id((entity.getId() == null ? null : TimeRecordId.of(entity.getId())))
                .build();
    }

    @Override
    public TimeRecordEntity toEntity(TimeRecord domainObject) {
        return TimeRecordEntity.builder()
                .status(domainObject.getStatus())
                .build();
    }
}
