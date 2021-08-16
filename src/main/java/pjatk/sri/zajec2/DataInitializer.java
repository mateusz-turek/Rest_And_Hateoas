package pjatk.sri.zajec2;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import pjatk.sri.zajec2.model.Company;
import pjatk.sri.zajec2.model.Employee;
import pjatk.sri.zajec2.model.Student;
import pjatk.sri.zajec2.model.ThesisSupervisor;
import pjatk.sri.zajec2.repository.CompanyRepository;
import pjatk.sri.zajec2.repository.EmployeeRepository;
import pjatk.sri.zajec2.repository.StudentRepository;
import pjatk.sri.zajec2.repository.ThesisSupervisorRepository;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DataInitializer implements ApplicationRunner {

    private final CompanyRepository companyRepository;
    private final EmployeeRepository employeeRepository;

    private final StudentRepository studentRepository;
    private final ThesisSupervisorRepository thesisSupervisorRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        List<String> subjects = Arrays.asList("s1", "s2", "s3");
        Student s1 = Student.builder()
                .firstName("sample student firstname 1")
                .birthDate(LocalDate.of(1990,01,01))
                .lastName("sample student lastname 1")
                .isLivingInDormitory(false)
                .subjectsList(subjects)
                .build();
        Student s2 = Student.builder()
                .firstName("sample student firstname 2")
                .birthDate(LocalDate.of(1990,01,01))
                .lastName("sample student lastname 2")
                .isLivingInDormitory(false)
                .subjectsList(subjects)
                .build();
        Student s3 = Student.builder()
                .firstName("sample student firstname 3")
                .birthDate(LocalDate.of(1990,01,01))
                .lastName("sample student lastname 3")
                .isLivingInDormitory(false)
                .subjectsList(subjects)
                .build();
        ThesisSupervisor ts1 = ThesisSupervisor.builder()
                .name("thesis supervisor name")
                .students(new HashSet<>())
                .build();
        ThesisSupervisor ts2 = ThesisSupervisor.builder()
                .name("thesis supervisor name")
                .students(new HashSet<>())
                .build();
        s1.setSupervisor(ts1);
        ts1.getStudents().add(s1);
        s2.setSupervisor(ts1);
        ts1.getStudents().add(s1);
        s3.setSupervisor(ts2);
        ts2.getStudents().add(s3);
        thesisSupervisorRepository.saveAll(Arrays.asList(ts1,ts2));
        studentRepository.saveAll(Arrays.asList(s1,s2,s3));

        Employee e1 = Employee.builder()
                .firstName("sampleName")
                .lastName("sampleLastname")
                .birthDate(LocalDate.of(1990,01,01))
                .job("sampleJob")
                .build();
        Employee e2 = Employee.builder()
                .firstName("sampleName")
                .lastName("sampleLastname")
                .birthDate(LocalDate.of(1990,01,01))
                .job("sampleJob")
                .build();
        Employee e3 = Employee.builder()
                .firstName("sampleName")
                .lastName("sampleLastname")
                .birthDate(LocalDate.of(1990,01,01))
                .job("sampleJob")
                .build();
        Company c1 = Company.builder().name("sampleCompany1").employees(new HashSet<>()).build();
        Company c2 = Company.builder().name("sampleCompany2").employees(new HashSet<>()).build();

        e1.setEmployer(c1);
        c1.getEmployees().add(e1);

        e2.setEmployer(c1);
        c1.getEmployees().add(e2);

        e3.setEmployer(c2);
        c2.getEmployees().add(e3);

        companyRepository.saveAll(Arrays.asList(c1,c2));
        employeeRepository.saveAll(Arrays.asList(e1,e2,e3));
    }
}
