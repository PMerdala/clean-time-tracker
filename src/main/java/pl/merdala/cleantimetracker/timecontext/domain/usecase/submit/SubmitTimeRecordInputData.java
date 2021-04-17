package pl.merdala.cleantimetracker.timecontext.domain.usecase.submit;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
public class SubmitTimeRecordInputData {

    private final LocalDate date;

    private final Integer minutes;

    private final Long taskId;

}
