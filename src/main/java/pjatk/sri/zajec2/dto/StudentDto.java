package pjatk.sri.zajec2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDto extends RepresentationModel<ThesisSupervisorDto> {
    private Long indexNumber;

    @NotBlank(message = "name is required")
    @Size(min = 2, max = 255)
    private String firstName;

    @NotBlank(message = "lastname is required")
    @Size(min = 2, max = 255)
    private String lastName;

    @Past(message = "birthdate should be past date")
    private LocalDate birthDate;

    private Boolean isLivingInDormitory;

    private List<String> subjectsList;
}
