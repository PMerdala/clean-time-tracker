package pl.merdala.cleantimetracker.projectcontext.domain.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class ProjectId {

    private final Long value;

    public static ProjectId of(Long id) {
        return new ProjectId(id);
    }

}
