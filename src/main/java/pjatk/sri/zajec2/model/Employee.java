package pjatk.sri.zajec2.model;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String job;

    @ManyToOne
    @JoinColumn(name = "employer_id")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Company employer;
}
