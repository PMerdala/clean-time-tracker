package pl.merdala.cleantimetracker.timecontext.domain.usecase.testdata;

import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecord;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecordId;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecordStatus;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeRecordTestData {
    public static final LocalDate FIRST_DATE = LocalDate.of(2020, Month.APRIL, 12);
    public static final LocalDate SECOND_DATE = LocalDate.of(2020, Month.APRIL, 14);
    public static final LocalDate THIRD_DATE = LocalDate.of(2020, Month.MAY, 14);
    ;
    public static final int FOUR_HOURS = 60 * 4;
    public static final int EIGHT_HOURS = 60 * 8;
    public static final int TWELVE_HOURS = 60 * 12;
    public static final long FIRST_TASK_ID = 1L;
    public static final long FIRST_TIME_RECORD_ID = 1L;

    public static List<TimeRecord> correct() {
        return Stream.of(
                TimeRecord.builder()
                        .id(TimeRecordId.of(FIRST_TIME_RECORD_ID))
                        .date(FIRST_DATE)
                        .minutes(FOUR_HOURS)
                        .taskId(FIRST_TASK_ID)
                        .status(TimeRecordStatus.OPEN)
                        .build(),
                TimeRecord.builder()
                        .id(TimeRecordId.of(FIRST_TIME_RECORD_ID+1))
                        .date(SECOND_DATE)
                        .minutes(FOUR_HOURS)
                        .taskId(FIRST_TASK_ID)
                        .status(TimeRecordStatus.RELEASED)
                        .build(),
                TimeRecord.builder()
                        .id(TimeRecordId.of(FIRST_TIME_RECORD_ID+2))
                        .date(FIRST_DATE)
                        .minutes(TWELVE_HOURS)
                        .taskId(FIRST_TASK_ID + 1)
                        .status(TimeRecordStatus.APPROVED)
                        .build(),
                TimeRecord.builder()
                        .id(TimeRecordId.of(FIRST_TIME_RECORD_ID+3))
                        .date(SECOND_DATE)
                        .minutes(TWELVE_HOURS)
                        .taskId(FIRST_TASK_ID + 1)
                        .status(TimeRecordStatus.OPEN)
                        .build(),
                TimeRecord.builder()
                        .id(TimeRecordId.of(FIRST_TIME_RECORD_ID+4))
                        .date(FIRST_DATE)
                        .minutes(EIGHT_HOURS)
                        .taskId(FIRST_TASK_ID + 2)
                        .status(TimeRecordStatus.RELEASED)
                        .build()
        ).collect(Collectors.toList());
    }
}
