package pjatk.sri.zajec2.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompanyDetailsDto extends RepresentationModel<CompanyDetailsDto> {

    private Long id;
    private String name;
    private List<EmployeeDto> employees;
}