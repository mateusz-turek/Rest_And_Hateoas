package pjatk.sri.zajec2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDto extends RepresentationModel<CompanyDto> {

    private Long id;
    private String name;
}
