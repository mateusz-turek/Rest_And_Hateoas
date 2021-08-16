package pjatk.sri.zajec2.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long indexNumber;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private Boolean isLivingInDormitory;
    @ElementCollection
    private List<String> subjectsList;

    @ManyToOne
    @JoinColumn(name = "supervisor_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ThesisSupervisor supervisor;
}
