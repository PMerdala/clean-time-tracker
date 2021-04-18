package pl.merdala.cleantimetracker.projectcontext.adapter.in.web.create;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateProjectForm {
    private String name;
}
