package pl.merdala.cleantimetracker.projectcontext.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.ProjectStatus;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "PROJECT")
class ProjectEntity {

    @Id
    @GeneratedValue
    @Column(name="ID")
    private Long id;

    @Column(name="NAME")
    private String name;

    @Column(name = "STATUS")
    @Enumerated(EnumType.STRING)
    private ProjectStatus status;
}
