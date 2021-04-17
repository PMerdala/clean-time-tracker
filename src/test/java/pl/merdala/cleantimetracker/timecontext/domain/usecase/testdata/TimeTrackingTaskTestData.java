package pl.merdala.cleantimetracker.timecontext.domain.usecase.testdata;

import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeTrackingTask;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class TimeTrackingTaskTestData {

    public static final long FIRST_TASK_ID = 10L;
    public static final long FIRST_PROJECT_ID = 100L;
    public static final String PREFIX_PROJECT_NAME = "projectName ";
    public static final String PREFIX_TASK_NAME = "name ";

    public static List<TimeTrackingTask> correct() {
        long task_id = FIRST_TASK_ID;
        long project_id = FIRST_PROJECT_ID;
        return Stream.of(
                TimeTrackingTask.builder()
                        .id(task_id)
                        .name(PREFIX_TASK_NAME + task_id)
                        .projectId(project_id)
                        .projectName(PREFIX_PROJECT_NAME + project_id)
                        .active(true)
                        .invoiceAble(true)
                        .build(),
                TimeTrackingTask.builder()
                        .id(++task_id)
                        .name(PREFIX_TASK_NAME + task_id)
                        .projectId(project_id)
                        .projectName(PREFIX_PROJECT_NAME + project_id)
                        .active(true)
                        .invoiceAble(false)
                        .build(),
                TimeTrackingTask.builder()
                        .id(++task_id)
                        .name(PREFIX_TASK_NAME + task_id)
                        .projectId(++project_id)
                        .projectName(PREFIX_PROJECT_NAME + project_id)
                        .active(false)
                        .invoiceAble(true)
                        .build(),
                TimeTrackingTask.builder()
                        .id(++task_id)
                        .name(PREFIX_TASK_NAME + task_id)
                        .projectId(project_id)
                        .projectName(PREFIX_PROJECT_NAME + project_id)
                        .active(false)
                        .invoiceAble(false)
                        .build()
        ).collect(Collectors.toList());
    }
}
