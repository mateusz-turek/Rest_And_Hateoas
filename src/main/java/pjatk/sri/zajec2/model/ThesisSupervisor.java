package pjatk.sri.zajec2.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ThesisSupervisor {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "supervisor")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Student> students;
}
