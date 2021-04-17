package pl.merdala.cleantimetracker.timecontext.domain.usecase.list;

import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@RequiredArgsConstructor
public class ListTimeRecordsQueryParameters {
    private final LocalDate start;
    private final LocalDate end;
}
