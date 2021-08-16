package pjatk.sri.zajec2.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ThesisSupervisorDto extends RepresentationModel<ThesisSupervisorDto> {

    private Long id;

    @NotBlank(message = "name is only field and it is required")
    @Size(min = 2, max = 255)
    private String name;
}
