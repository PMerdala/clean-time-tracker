package pl.merdala.cleantimetracker.timecontext.adapter.in.web.list;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeRecordWithTask;
import pl.merdala.cleantimetracker.timecontext.domain.entity.TimeTrackingTask;
import pl.merdala.cleantimetracker.timecontext.domain.usecase.list.ListTimeRecordsQueryParameters;
import pl.merdala.cleantimetracker.timecontext.domain.usecase.list.ListTimeRecordsUseCase;
import pl.merdala.cleantimetracker.timecontext.domain.usecase.submit.SubmitTimeRecordInputData;
import pl.merdala.cleantimetracker.timecontext.domain.usecase.submit.SubmitTimeRecordsUseCase;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class ListTimeRecordsController {

    private final ListTimeRecordsUseCase listTimeRecordsUseCase;

    private final SubmitTimeRecordsUseCase submitTimeRecordsUseCase;

    public ListTimeRecordsController(ListTimeRecordsUseCase listTimeRecordsUseCase
            , SubmitTimeRecordsUseCase submitTimeRecordsUseCase) {
        this.listTimeRecordsUseCase = listTimeRecordsUseCase;
        this.submitTimeRecordsUseCase = submitTimeRecordsUseCase;
    }

    @GetMapping("/time-records")
    String displayTimeRecordsList(Model model) {
        model.addAttribute("submitTimeRecordForm", new SubmitTimeRecordForm());
        model.addAttribute("tasks", getAvailableTasks());
        model.addAttribute("records", getStoredTimeRecords());
        return "time-records/listTimeRecords.html";
    }

    @PostMapping("/time-records")
    String submitTimeRecord(@Valid SubmitTimeRecordForm form) {
        SubmitTimeRecordInputData input = SubmitTimeRecordInputData.builder()
                .date(form.getDate())
                .minutes(Math.round(form.getHours() * 60))
                .taskId(form.getTaskId())
                .build();
        submitTimeRecordsUseCase.submitTimeRecords(Collections.singletonList(input));
        return "redirect:/time-records";
    }

    private List<TimeRecordListModel> getStoredTimeRecords() {
        List<TimeRecordWithTask> records = listTimeRecordsUseCase.listTimeRecords(
                ListTimeRecordsQueryParameters.builder()
                        .start(LocalDate.now().minusDays(15))
                        .end(LocalDate.now().plusDays(15))
                        .build()
        );
        return records.stream().map(record -> TimeRecordListModel.builder()
                .date(record.getDate())
                .hours(record.getMinutes() / (float) 60)
                .taskName(record.getTask().getName())
                .build()).collect(Collectors.toList());
    }

    private List<TaskModel> getAvailableTasks() {
        List<TimeTrackingTask> tasks = listTimeRecordsUseCase.listAllTasks();
        return tasks.stream().map(task -> TaskModel.builder()
                .id(task.getId())
                .name(task.getName())
                .build())
                .collect(Collectors.toList());
    }
}
