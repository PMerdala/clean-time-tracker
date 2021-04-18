package pl.merdala.cleantimetracker.timecontext.domain.usecase.list;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecord;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecordWithTask;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeTrackingTask;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.QueryTasksPort;
import pl.merdala.cleantimetracker.timecontext.domain.port.out.persistence.QueryTimeRecordsPort;
import pl.merdala.cleantimetracker.timecontext.domain.usecase.testdata.TimeRecordTestData;
import pl.merdala.cleantimetracker.timecontext.domain.usecase.testdata.TimeTrackingTaskTestData;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ListTimeRecordsUseCaseTest {

    public static final LocalDate FIRST_DATE = LocalDate.of(2020, Month.APRIL, 13);
    public static final LocalDate SECOND_DATE = LocalDate.of(2020, Month.APRIL, 14);
    public static final int MAX_PERIOD_INTERVAL = 31;
    @Mock
    QueryTimeRecordsPort queryTimeRecordsPort;

    @Mock
    QueryTasksPort queryTasksPort;

    ListTimeRecordsUseCase sut;

    @BeforeEach
    void setUp() {
        sut = new ListTimeRecordsUseCase(queryTimeRecordsPort, queryTasksPort);
    }

    @Test
    void listTimeRecordsWithEmptyPersist() {
        ListTimeRecordsQueryParameters queryParam = new ListTimeRecordsQueryParameters(FIRST_DATE, SECOND_DATE);
        when(queryTimeRecordsPort.listTimeRecords(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(emptyList());
        when(queryTasksPort.listByIds(anySet())).thenReturn(emptyList());
        assertTrue(sut.listTimeRecords(queryParam).isEmpty());
    }

    @Test
    void listTimeRecordsWithCorrectPersistData() {
        ListTimeRecordsQueryParameters queryParam = new ListTimeRecordsQueryParameters(FIRST_DATE, SECOND_DATE);
        List<TimeRecord> timeRecords = TimeRecordTestData.correct();
        when(queryTimeRecordsPort.listTimeRecords(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(timeRecords);
        when(queryTasksPort.listByIds(anySet())).thenReturn(TimeTrackingTaskTestData.correct());
        List<TimeRecordWithTask> recordWithTasks = sut.listTimeRecords(queryParam);
        assertEquals(timeRecords.size(), recordWithTasks.size());
    }

    @Test
    void listTimeRecordsStartEqualEnd() {
        ListTimeRecordsQueryParameters queryParam = new ListTimeRecordsQueryParameters(FIRST_DATE, SECOND_DATE.minusDays(1));
        when(queryTimeRecordsPort.listTimeRecords(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(emptyList());
        when(queryTasksPort.listByIds(anySet())).thenReturn(emptyList());
        sut.listTimeRecords(queryParam);
    }

    @Test
    void listTimeRecordsWithMaxPeriodInterval() {
        ListTimeRecordsQueryParameters queryParam = new ListTimeRecordsQueryParameters(FIRST_DATE, FIRST_DATE.plusDays(MAX_PERIOD_INTERVAL));
        when(queryTimeRecordsPort.listTimeRecords(any(LocalDate.class), any(LocalDate.class)))
                .thenReturn(emptyList());
        when(queryTasksPort.listByIds(anySet())).thenReturn(emptyList());
        sut.listTimeRecords(queryParam);
    }

    @Test
    void listTimeRecordsQueryParamsEndBeforeStartThrowException() {
        ListTimeRecordsQueryParameters queryParam = new ListTimeRecordsQueryParameters(SECOND_DATE, FIRST_DATE);
        assertThrows(IntervalEndBeforeStartException.class, () -> sut.listTimeRecords(queryParam));
    }

    @Test
    void listTimeRecordsQueryParamsPeriodTooLong() {
        ListTimeRecordsQueryParameters queryParam = new ListTimeRecordsQueryParameters(FIRST_DATE, FIRST_DATE.plusDays(MAX_PERIOD_INTERVAL + 1));
        assertThrows(IntervalTooLongException.class, () -> sut.listTimeRecords(queryParam));
    }

    @Test
    void listAllTasks() {
        List<TimeTrackingTask> expectedTimeTrackingTasks = TimeTrackingTaskTestData.correct();
        when(queryTasksPort.listAllTasks()).thenReturn(expectedTimeTrackingTasks);
        List<TimeTrackingTask> timeTrackingTasks = sut.listAllTasks();
        assertEquals(expectedTimeTrackingTasks,timeTrackingTasks);
    }
}