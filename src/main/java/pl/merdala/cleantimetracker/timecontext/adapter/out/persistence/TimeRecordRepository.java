package pl.merdala.cleantimetracker.timecontext.adapter.out.persistence;

import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public interface TimeRecordRepository extends CrudRepository<TimeRecordEntity,Long> {

    List<TimeRecordEntity> findByDateBetween(LocalDate start, LocalDate end);

    List<TimeRecordEntity> findByDateIn(Iterable<LocalDate> dates);
}
