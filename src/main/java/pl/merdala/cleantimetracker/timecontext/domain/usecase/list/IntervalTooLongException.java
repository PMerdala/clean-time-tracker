package pl.merdala.cleantimetracker.timecontext.domain.usecase.list;

import java.time.LocalDate;

public class IntervalTooLongException extends RuntimeException {
    public IntervalTooLongException(LocalDate start, LocalDate end, int maxDays) {
        super(String.format("interval must not be longer then %d days (start: %s, end:%s", maxDays, start, end));
    }
}
