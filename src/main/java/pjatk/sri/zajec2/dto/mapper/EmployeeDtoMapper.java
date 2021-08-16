package pjatk.sri.zajec2.dto.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pjatk.sri.zajec2.dto.CompanyDetailsDto;
import pjatk.sri.zajec2.dto.CompanyDto;
import pjatk.sri.zajec2.dto.EmployeeDto;
import pjatk.sri.zajec2.model.Company;
import pjatk.sri.zajec2.model.Employee;

@Component
@RequiredArgsConstructor
public class EmployeeDtoMapper {
    private final ModelMapper modelMapper;

    public EmployeeDto convertToDto(Employee e){
        return modelMapper.map(e, EmployeeDto.class);
    }

    public Employee convertToEntity(EmployeeDto dto){
        return modelMapper.map(dto, Employee.class);
    }
}
