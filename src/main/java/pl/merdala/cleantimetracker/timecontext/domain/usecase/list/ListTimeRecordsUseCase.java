package pl.merdala.cleantimetracker.timecontext.domain.usecase.list;

import pl.merdala.cleantimetracker.annotation.UseCase;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecord;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecordWithTask;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeTrackingTask;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.QueryTasksPort;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.persistence.QueryTimeRecordsPort;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toMap;

@UseCase
public class ListTimeRecordsUseCase {
    private static final int INTERVAL_MAXIMUM_DAYS = 31;

    private final QueryTimeRecordsPort queryTimeRecordsPort;
    private final QueryTasksPort queryTasksPort;

    public ListTimeRecordsUseCase(QueryTimeRecordsPort queryTimeRecordsPort, QueryTasksPort queryTasksPort) {
        this.queryTimeRecordsPort = queryTimeRecordsPort;
        this.queryTasksPort = queryTasksPort;
    }

    public List<TimeRecordWithTask> listTimeRecords(ListTimeRecordsQueryParameters queryParam) {
        rejectIfEndBeforeStart(queryParam);
        rejectIfPeriodToLong(queryParam);
        List<TimeRecord> timeRecords = queryTimeRecordsPort.listTimeRecords(queryParam.getStart(), queryParam.getEnd());
        return expandTasks(timeRecords);
    }

    public List<TimeTrackingTask> listAllTasks() {
        return queryTasksPort.listAllTasks();
    }

    private void rejectIfPeriodToLong(ListTimeRecordsQueryParameters queryParam) {
        if(queryParam.getStart().plusDays(INTERVAL_MAXIMUM_DAYS).isBefore(queryParam.getEnd())){
            throw new IntervalTooLongException(queryParam.getStart(),queryParam.getEnd(),INTERVAL_MAXIMUM_DAYS);
        }
    }

    private void rejectIfEndBeforeStart(ListTimeRecordsQueryParameters queryParam) {
        if (queryParam.getStart().isAfter(queryParam.getEnd())){
            throw new IntervalEndBeforeStartException(queryParam.getStart(),queryParam.getEnd());
        }
    }

    private List<TimeRecordWithTask> expandTasks(List<TimeRecord> timeRecords) {
        Set<Long> taskIds = timeRecords.stream()
                .map(TimeRecord::getTaskId)
                .collect(Collectors.toSet());

        Map<Long, TimeTrackingTask> taskById = queryTasksPort.listByIds(taskIds).stream()
                .collect(toMap(TimeTrackingTask::getId, timeTrackingTask -> timeTrackingTask));

        return timeRecords.stream()
                .map((record -> TimeRecordWithTask.fromTimeRecord(record, taskById.get(record.getTaskId()))))
                .collect(Collectors.toList());
    }

}
