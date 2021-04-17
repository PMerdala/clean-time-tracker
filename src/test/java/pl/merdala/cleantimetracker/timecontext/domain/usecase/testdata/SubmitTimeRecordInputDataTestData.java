package pl.merdala.cleantimetracker.timecontext.domain.usecase.testdata;

import pl.merdala.cleantimetracker.timecontext.domain.usecase.submit.SubmitTimeRecordInputData;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class SubmitTimeRecordInputDataTestData {
    public static final LocalDate FIRST_DATE= LocalDate.of(2020, Month.APRIL, 12);
    public static final LocalDate SECOND_DATE = LocalDate.of(2020, Month.APRIL, 14);
    public static final LocalDate THIRD_DATE = LocalDate.of(2020, Month.MAY, 14);;
    public static final int FOUR_HOURS = 60 * 4;
    public static final int EIGHT_HOURS = 60 * 8;
    public static final int TWELVE_HOURS = 60 * 12;
    public static final long FIRST_TASK_ID = 1L;

    public static List<SubmitTimeRecordInputData> incorrectTooManyMinutesPerDate() {
        List<SubmitTimeRecordInputData> records = correct();
        records.add(new SubmitTimeRecordInputData(SECOND_DATE, EIGHT_HOURS + 1, FIRST_TASK_ID + 2));
        return records;
    }

    public static List<SubmitTimeRecordInputData> incorrectTooManyMinutesPerRecord() {
        List<SubmitTimeRecordInputData> records = correct();
        records.add(new SubmitTimeRecordInputData(THIRD_DATE, TWELVE_HOURS + 1, FIRST_TASK_ID));
        return records;
    }

    public static List<SubmitTimeRecordInputData> correct() {
        return  TimeRecordTestData.correct().stream()
                .map(record ->new SubmitTimeRecordInputData(record.getDate(),record.getMinutes(),record.getTaskId()))
                .collect(Collectors.toList());
    }
}