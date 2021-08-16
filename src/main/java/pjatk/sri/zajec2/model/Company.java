package pjatk.sri.zajec2.model;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "employer")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Set<Employee> employees;
}
