package pl.merdala.cleantimetracker.timecontext.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TimeRecordId implements Serializable {

    private final Long value;

    public static TimeRecordId of(Long id){
        return new TimeRecordId(id);
    }
}
