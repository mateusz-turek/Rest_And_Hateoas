package pjatk.sri.zajec2.rest;

import org.modelmapper.ModelMapper;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import pjatk.sri.zajec2.dto.StudentDto;
import pjatk.sri.zajec2.model.Student;
import pjatk.sri.zajec2.repository.StudentRepository;

import javax.validation.Valid;
import java.net.URI;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/students")
public class StudentController {
    private ModelMapper modelMapper;
    private StudentRepository studentRepository;

    @GetMapping
    public ResponseEntity<Collection<StudentDto>> getStudents(){
        List<Student> studentsList = studentRepository.findAll();
        List<StudentDto> results = studentsList.stream().map(this::convertToDto).collect(Collectors.toList());
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/{studentIndex}")
    public ResponseEntity<StudentDto> getStudentByIndex(@PathVariable Long studentIndex){
        Optional<Student> studentOptional = studentRepository.findById(studentIndex);
        if (studentOptional.isPresent()){
            StudentDto studentDto = convertToDto(studentOptional.get());
            return new ResponseEntity<>(studentDto, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping()
    public ResponseEntity saveNewStudent(@Valid @RequestBody StudentDto studentDto){
        Student newStudent = convertToEntity(studentDto);
        studentRepository.save(newStudent);
        HttpHeaders headers = new HttpHeaders();
        URI location = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(newStudent.getIndexNumber())
                .toUri();
        headers.add("Location",location.toString());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @PutMapping("/{studentIndex}")
    public ResponseEntity updateStudent(@PathVariable Long studentIndex,@Valid @RequestBody StudentDto studentDto){
        Optional<Student> optionalStudentToUpdate = studentRepository.findById(studentIndex);
        if (optionalStudentToUpdate.isPresent()){
            studentDto.setIndexNumber(studentIndex);
            Student student = convertToEntity(studentDto);
            studentRepository.save(student);
            return new ResponseEntity(studentDto,HttpStatus.NO_CONTENT);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{studentIndex}")
    public ResponseEntity deleteStudent(@PathVariable Long studentIndex){
        studentRepository.deleteById(studentIndex);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    public StudentController(ModelMapper modelMapper, StudentRepository studentRepository) {
        this.modelMapper = modelMapper;
        this.studentRepository = studentRepository;
    }

    private StudentDto convertToDto(Student student){
        return modelMapper.map(student, StudentDto.class);
    }

    private Student convertToEntity(StudentDto studentDto){
        return modelMapper.map(studentDto, Student.class);
    }
}
