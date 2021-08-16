package pjatk.sri.zajec2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {
    private Long id;
    @NotBlank(message = "first name is required")
    @Size(min = 2, max = 255)
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private String job;
}
