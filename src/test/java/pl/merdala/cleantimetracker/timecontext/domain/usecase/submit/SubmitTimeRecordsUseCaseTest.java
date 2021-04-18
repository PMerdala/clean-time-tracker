package pl.merdala.cleantimetracker.timecontext.domain.usecase.submit;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecord;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecordId;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecordStatus;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.QueryTasksPort;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.persistence.QueryTimeRecordsPort;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.persistence.SaveTimeRecordsPort;
import pl.merdala.cleantimetracker.timecontext.domain.usecase.testdata.SubmitTimeRecordInputDataTestData;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class SubmitTimeRecordsUseCaseTest {

    @Mock
    SaveTimeRecordsPort saveTimeRecordsPort;

    @Mock
    QueryTimeRecordsPort queryTimeRecordsPort;

    @Mock
    QueryTasksPort queryTasksPort;


    SubmitTimeRecordsUseCase sut;

    @BeforeEach
    void setUp() {
        sut = new SubmitTimeRecordsUseCase(saveTimeRecordsPort, queryTimeRecordsPort, queryTasksPort);
    }

    @Test
    void submitTimeRecordsWhenRecordIsCorrectSaveTimeRecordsPortIsInvoke() {
        List<SubmitTimeRecordInputData> records = correct();
        sut.submitTimeRecords(records);
        verify(saveTimeRecordsPort).saveTimeRecords(anyList());
    }

    @Test
    void submitTimeRecordsWhenSomeRecordHasTooManyMinutesThrowException() {
        List<SubmitTimeRecordInputData> records = incorrectTooManyMinutesPerRecord();
        assertThrows(TooMuchTimePerRecordException.class, () -> sut.submitTimeRecords(records));
    }

    @Test
    void submitTimeRecordsWhenSumOfTimePerDateIsTooMuchThrowException() {
        List<SubmitTimeRecordInputData> records = incorrectTooManyMinutesPerDate();
        assertThrows(TooMuchTimePerDayException.class, () -> sut.submitTimeRecords(records));
    }

    @Test
    void submitTimeRecordsWhenSumOfTimePerDateIsTooMuchWithPersistDataThrowException() {
        List<SubmitTimeRecordInputData> records = correct();
        when(queryTimeRecordsPort.listByDates(anySet())).thenReturn(Stream.of(
                TimeRecord.builder()
                .date(SubmitTimeRecordInputDataTestData.SECOND_DATE)
                .minutes(SubmitTimeRecordInputDataTestData.EIGHT_HOURS + 1)
                .taskId(SubmitTimeRecordInputDataTestData.FIRST_TASK_ID +2)
                .status(TimeRecordStatus.RELEASED)
                .id(TimeRecordId.of(1L))
                .build()
        ).collect(Collectors.toList()));
        assertThrows(TooMuchTimePerDayException.class, () -> sut.submitTimeRecords(records));
    }

    @Test
    void submitTimeRecordsWhenMultipleRecordsPerTaskAndDayThenThrowException() {
        List<SubmitTimeRecordInputData> records = correct();
        records.add(new SubmitTimeRecordInputData(SubmitTimeRecordInputDataTestData.SECOND_DATE, SubmitTimeRecordInputDataTestData.FOUR_HOURS, SubmitTimeRecordInputDataTestData.FIRST_TASK_ID));
        assertThrows(TooManyRecordsPerDayException.class, () -> sut.submitTimeRecords(records));
    }

    @Test
    void submitTimeRecordsWhenMultipleRecordsPerTaskAndDayWithPersistDataThrowException() {
        List<SubmitTimeRecordInputData> records = correct();
        when(queryTimeRecordsPort.listByDates(anySet())).thenReturn(Stream.of(
                TimeRecord.builder()
                        .date(SubmitTimeRecordInputDataTestData.SECOND_DATE)
                        .minutes(SubmitTimeRecordInputDataTestData.FOUR_HOURS)
                        .taskId(SubmitTimeRecordInputDataTestData.FIRST_TASK_ID)
                        .status(TimeRecordStatus.RELEASED)
                        .id(TimeRecordId.of(1L))
                        .build()
        ).collect(Collectors.toList()));
        assertThrows(TooManyRecordsPerDayException.class, () -> sut.submitTimeRecords(records));
    }

    private List<SubmitTimeRecordInputData> incorrectTooManyMinutesPerDate() {
        return SubmitTimeRecordInputDataTestData.incorrectTooManyMinutesPerDate();
    }

    private List<SubmitTimeRecordInputData> incorrectTooManyMinutesPerRecord() {
        return SubmitTimeRecordInputDataTestData.incorrectTooManyMinutesPerRecord();
    }

    private List<SubmitTimeRecordInputData> correct() {
        return SubmitTimeRecordInputDataTestData.correct();
    }
}