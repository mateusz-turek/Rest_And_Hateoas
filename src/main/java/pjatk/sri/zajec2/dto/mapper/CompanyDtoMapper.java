package pjatk.sri.zajec2.dto.mapper;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pjatk.sri.zajec2.dto.CompanyDetailsDto;
import pjatk.sri.zajec2.dto.CompanyDto;
import pjatk.sri.zajec2.model.Company;

@Component
@RequiredArgsConstructor
public class CompanyDtoMapper {

    private final ModelMapper modelMapper;

    public Company convertToEntity(CompanyDto dto){
        return modelMapper.map(dto,Company.class);
    }

    public CompanyDto convertToDto(Company company){
        return modelMapper.map(company,CompanyDto.class);
    }

    public CompanyDetailsDto convertToDetailsDto(Company company){
        return modelMapper.map(company, CompanyDetailsDto.class);
    }
}
