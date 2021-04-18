package pl.merdala.cleantimetracker.timecontext.domain.usecase.submit;

import lombok.Value;
import pl.merdala.cleantimetracker.annotation.UseCase;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecord;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecordStatus;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.QueryTasksPort;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.persistence.QueryTimeRecordsPort;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.persistence.SaveTimeRecordsPort;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.*;

@UseCase
public class SubmitTimeRecordsUseCase {

    private static final int MAXIMUM_MINUTES_PER_RECORD = 12 * 60;

    private static final int MAXIMUM_MINUTES_PER_DAY = 24 * 60;

    private static final int MAXIMUM_RECORDS_PER_TASK_AND_DAY = 1;

    private final SaveTimeRecordsPort saveTimeRecordsPort;

    private final QueryTasksPort queryTasksPort;

    private final QueryTimeRecordsPort queryTimeRecordsPort;

    public SubmitTimeRecordsUseCase(SaveTimeRecordsPort saveTimeRecordsPort, QueryTimeRecordsPort queryTimeRecordsPort, QueryTasksPort queryTasksPort) {
        this.saveTimeRecordsPort = saveTimeRecordsPort;
        this.queryTimeRecordsPort = queryTimeRecordsPort;
        this.queryTasksPort = queryTasksPort;
    }

    public void submitTimeRecords(List<SubmitTimeRecordInputData> records) {
        rejectRecordsWhenTooManyMinutesOnSingleRecord(records);
        rejectRecordsWHenTooManyMinutesOnDate(records);
        rejectMultipleRecordsPerTaskAndDay(records);
        saveTimeRecordsPort.saveTimeRecords(toTimeRecords(records));
    }

    private void rejectMultipleRecordsPerTaskAndDay(List<SubmitTimeRecordInputData> records) {
        Map<LocalDate, Map<Long, Long>> newRecordsPerDayAndTasks = records.stream()
                .collect(groupingBy(SubmitTimeRecordInputData::getDate,
                        groupingBy(SubmitTimeRecordInputData::getTaskId,
                                counting())));

        Map<LocalDate, Map<Long, Long>> persistRecordsPerDayANdTasks = queryTimeRecordsPort
                .listByDates(newRecordsPerDayAndTasks.keySet()).stream()
                .collect(groupingBy(TimeRecord::getDate,
                        groupingBy(TimeRecord::getTaskId,
                                counting())));

        Map<LocalDate, Map<Long, Long>> recordsPerDayAndTasks = Stream.concat(
                newRecordsPerDayAndTasks.entrySet().stream(),
                persistRecordsPerDayANdTasks.entrySet().stream())
                .map(data -> data.getValue().entrySet().stream()
                        .map(l -> new Triple<>(data.getKey(), l.getKey(), l.getValue())).collect(Collectors.toList()))
                .flatMap(Collection::stream)
                .collect(groupingBy(Triple::getLeft, groupingBy(Triple::getMiddle, summingLong(Triple::getRight))));

        recordsPerDayAndTasks.forEach((date, map) -> map.forEach((id, count) -> {
            if (count > MAXIMUM_RECORDS_PER_TASK_AND_DAY) {
                throw new TooManyRecordsPerDayException();
            }
        }));
    }

    private void rejectRecordsWHenTooManyMinutesOnDate(List<SubmitTimeRecordInputData> records) {
        Map<LocalDate, Integer> newMinutesPerDay = records.stream()
                .collect(groupingBy(SubmitTimeRecordInputData::getDate, summingInt(SubmitTimeRecordInputData::getMinutes)));

        Map<LocalDate, Integer> persistMinutesPerDate = queryTimeRecordsPort.listByDates(newMinutesPerDay.keySet()).stream()
                .collect(groupingBy(TimeRecord::getDate, summingInt(TimeRecord::getMinutes)));

        Map<LocalDate, Integer> minutesPerDay = Stream.concat(
                newMinutesPerDay.entrySet().stream(),
                persistMinutesPerDate.entrySet().stream())
                .collect(groupingBy(Map.Entry::getKey, summingInt(Map.Entry::getValue)));

        minutesPerDay.forEach((date, minutes) -> {
            if (minutes > MAXIMUM_MINUTES_PER_DAY) {
                throw new TooMuchTimePerDayException(minutes, date, MAXIMUM_MINUTES_PER_DAY);
            }
        });
    }

    private void rejectRecordsWhenTooManyMinutesOnSingleRecord(List<SubmitTimeRecordInputData> records) {
        records.forEach(r -> {
            if (r.getMinutes() > MAXIMUM_MINUTES_PER_RECORD) {
                throw new TooMuchTimePerRecordException(r.getMinutes(), MAXIMUM_MINUTES_PER_RECORD);
            }
        });
    }

    private List<TimeRecord> toTimeRecords(List<SubmitTimeRecordInputData> records) {
        return records.stream().map(r -> TimeRecord.builder()
                .date(r.getDate())
                .minutes(r.getMinutes())
                .taskId(r.getTaskId())
                .status(TimeRecordStatus.OPEN)
                .build()).collect(Collectors.toList());
    }

    @Value
    private static class Triple<L, M, R> {

        L left;
        M middle;
        R right;

    }
}
