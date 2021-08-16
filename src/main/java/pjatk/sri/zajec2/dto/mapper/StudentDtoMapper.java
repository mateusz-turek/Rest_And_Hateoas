package pjatk.sri.zajec2.dto.mapper;


import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import pjatk.sri.zajec2.dto.StudentDto;
import pjatk.sri.zajec2.model.Student;

@Component
@RequiredArgsConstructor
public class StudentDtoMapper {
    private final ModelMapper modelMapper;

    public StudentDto convertToDto (Student student){
        return modelMapper.map(student, StudentDto.class);
    }

    public Student convertToEntity(StudentDto studentDto){
        return modelMapper.map(studentDto, Student.class);
    }
}
