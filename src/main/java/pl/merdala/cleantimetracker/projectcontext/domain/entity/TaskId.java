package pl.merdala.cleantimetracker.projectcontext.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.io.Serializable;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class TaskId implements Serializable {
    private final Long value;

    public static TaskId of(Long id){
        return new TaskId(id);
    }
}
