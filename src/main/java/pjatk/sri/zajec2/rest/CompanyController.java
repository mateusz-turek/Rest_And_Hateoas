package pjatk.sri.zajec2.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pjatk.sri.zajec2.dto.CompanyDetailsDto;
import pjatk.sri.zajec2.dto.CompanyDto;
import pjatk.sri.zajec2.dto.EmployeeDto;
import pjatk.sri.zajec2.dto.mapper.CompanyDtoMapper;
import pjatk.sri.zajec2.dto.mapper.EmployeeDtoMapper;
import pjatk.sri.zajec2.model.Company;
import pjatk.sri.zajec2.model.Employee;
import pjatk.sri.zajec2.repository.CompanyRepository;
import pjatk.sri.zajec2.repository.EmployeeRepository;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/companies")
@RequiredArgsConstructor
public class CompanyController {

    private final EmployeeRepository employeeRepository;
    private final EmployeeDtoMapper employeeDtoMapper;
    private final CompanyRepository companyRepository;
    private final CompanyDtoMapper companyDtoMapper;

    @GetMapping
    public ResponseEntity<CollectionModel<CompanyDto>> getCompanies(){
        List<Company> allCompanies = companyRepository.findAll();
        List<CompanyDto> result = allCompanies.stream()
                .map(companyDtoMapper::convertToDto)
                .collect(Collectors.toList());
        for(CompanyDto dto: result){
            dto.add(createSelfLink(dto.getId()));
            dto.add(createEmployeesLink(dto.getId()));
        }

        Link linkCollectionSelf = linkTo(methodOn(CompanyController.class).getCompanies()).withSelfRel();
        CollectionModel<CompanyDto> resultWithLink = CollectionModel.of(result, linkCollectionSelf);
        return new ResponseEntity<>(resultWithLink, HttpStatus.OK);
    }

    @GetMapping(value = "/{companyId}")
    public ResponseEntity<CompanyDetailsDto> getCompanyDetails(@PathVariable("companyId") Long companyId){
        Optional<Company> byId = companyRepository.findById(companyId);
        if(byId.isPresent()){
            CompanyDetailsDto detailsDto = companyDtoMapper.convertToDetailsDto(byId.get());
            detailsDto.add(createSelfLink(companyId));
            return new ResponseEntity<>(detailsDto,HttpStatus.OK);
        } else{
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/{companyId}/employees")
    public ResponseEntity getEmployeesByCompany(@PathVariable("companyId") Long companyId){
        List<Employee> employees = employeeRepository.findEmployeeByEmployerId(companyId);
        List<EmployeeDto> results = employees.stream()
                .map(employeeDtoMapper::convertToDto)
                .collect(Collectors.toList());
        return new ResponseEntity<>(results,HttpStatus.OK);
    }

    private Link createSelfLink(Long companyId){
        return linkTo(methodOn(CompanyController.class).getCompanyDetails(companyId)).withSelfRel();
    }

    private Link createEmployeesLink(Long companyId){
        return linkTo(methodOn(CompanyController.class).getEmployeesByCompany(companyId)).withRel("employees");
    }
}
