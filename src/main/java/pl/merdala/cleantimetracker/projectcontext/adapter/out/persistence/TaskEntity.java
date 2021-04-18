package pl.merdala.cleantimetracker.projectcontext.adapter.out.persistence;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.merdala.cleantimetracker.projectcontext.domain.entity.TaskStatus;

import javax.persistence.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "TASK")
public class TaskEntity {

    @Id
    @GeneratedValue
    @Column(name = "ID")
    private Long id;

    @Column(name = "NAME")
    private String name;

    @Column(name = "INVOICEABLE")
    private Boolean invoiceable;

    @ManyToOne(optional = false)
    @JoinColumn(name = "PROJECT_ID")
    private ProjectEntity project;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS")
    private TaskStatus status;
}
