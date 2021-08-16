package pjatk.sri.zajec2.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pjatk.sri.zajec2.dto.StudentDto;
import pjatk.sri.zajec2.dto.ThesisSupervisorDetailsDto;
import pjatk.sri.zajec2.dto.ThesisSupervisorDto;
import pjatk.sri.zajec2.dto.mapper.StudentDtoMapper;
import pjatk.sri.zajec2.dto.mapper.ThesisSupervisorDtoMapper;
import pjatk.sri.zajec2.model.Student;
import pjatk.sri.zajec2.model.ThesisSupervisor;
import pjatk.sri.zajec2.repository.StudentRepository;
import pjatk.sri.zajec2.repository.ThesisSupervisorRepository;

import javax.validation.Valid;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;



@RestController
@RequestMapping("/api/ts")
@RequiredArgsConstructor
public class ThesisSupervisorController {
    private final StudentRepository studentRepository;
    private final StudentDtoMapper studentDtoMapper;

    private final ThesisSupervisorRepository thesisSupervisorRepository;
    private final ThesisSupervisorDtoMapper thesisSupervisorDtoMapper;

    @GetMapping
    public ResponseEntity<CollectionModel<ThesisSupervisorDto>> getSupervisors(){
        List<ThesisSupervisor> thesisSupervisorList = thesisSupervisorRepository.findAll();
        List<ThesisSupervisorDto> result = thesisSupervisorList.stream()
                .map(thesisSupervisorDtoMapper::convertToDto)
                .collect(Collectors.toList());
        for(ThesisSupervisorDto thesisSupervisorDto: result){
            thesisSupervisorDto.add(createSelfLink(thesisSupervisorDto.getId()));
            thesisSupervisorDto.add(createStudentLink(thesisSupervisorDto.getId()));
        }

        Link self = linkTo(methodOn(ThesisSupervisorController.class).getSupervisors()).withSelfRel();
        CollectionModel<ThesisSupervisorDto> resultWithLink = CollectionModel.of(result,self);
        return new ResponseEntity<>(resultWithLink, HttpStatus.OK);
    }

    @GetMapping(value = "/{tsId}")
    public ResponseEntity<ThesisSupervisorDetailsDto> getSupervisorById(@PathVariable("tsId") Long tsId){
        Optional<ThesisSupervisor> thesisSupervisor = thesisSupervisorRepository.findById(tsId);
        if(thesisSupervisor.isPresent()){
            ThesisSupervisorDetailsDto thesisSupervisorDetailsDto = thesisSupervisorDtoMapper.convertToDetailsDto(thesisSupervisor.get());
            thesisSupervisorDetailsDto.add(createSelfLink(tsId));
            return new ResponseEntity<>(thesisSupervisorDetailsDto,HttpStatus.OK);
        } else {
            return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{tsId}/students")
    public ResponseEntity getStudentsByThesisSupervisor(@PathVariable("tsId") Long tsId){
        List<Student> students = studentRepository.findStudentsBySupervisorId(tsId);
        List<StudentDto> studentDtos = students.stream()
                .map(studentDtoMapper::convertToDto)
                .collect(Collectors.toList());
        for (StudentDto sDto: studentDtos) {
            sDto.add(createStudentLink(tsId));
        }
        return new ResponseEntity<>(studentDtos,HttpStatus.OK);
    }

    @PostMapping()
    public ResponseEntity saveNewThesisSupervisor(@Valid @RequestBody ThesisSupervisorDto supervisorDto){
        ThesisSupervisor newTS = thesisSupervisorDtoMapper.convertToEntity(supervisorDto);
        thesisSupervisorRepository.save(newTS);
        ThesisSupervisorDto dto = thesisSupervisorDtoMapper.convertToDto(newTS);
        dto.add(createSelfLink(dto.getId()).withRel("to New supervisor"));
        dto.add(createSelfLinkToApi().withRel("api rel"));
        return new ResponseEntity(dto,HttpStatus.CREATED);
    }

    @PutMapping("/{tsId}")
    public ResponseEntity updateThesisSupervisor(@PathVariable Long tsId,@Valid @RequestBody ThesisSupervisorDto thesisSupervisorDto){
        Optional<ThesisSupervisor> optionalTs = thesisSupervisorRepository.findById(tsId);
        if (optionalTs.isPresent()){
            thesisSupervisorDto.setId(tsId);
            ThesisSupervisor supervisor = thesisSupervisorDtoMapper.convertToEntity(thesisSupervisorDto);
            thesisSupervisorRepository.save(supervisor);
            thesisSupervisorDto.add(createSelfLinkToApi().withRel("api rel"));
            return new ResponseEntity(thesisSupervisorDto,HttpStatus.CREATED);
        }else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{updateRelation}/{tsId}/{studentIndex}")
    public ResponseEntity updateThesisSupervisor(@PathVariable Long tsId,@PathVariable Long studentIndex,@PathVariable String updateRelation){
        Optional<Student> optionalStudent = studentRepository.findByIndexNumber(studentIndex);
        Optional<ThesisSupervisor> optionalTs = thesisSupervisorRepository.findById(tsId);
        if (optionalTs.isPresent() && optionalStudent.isPresent()){
            Student student = optionalStudent.get();
            ThesisSupervisor thesisSupervisor = optionalTs.get();
            if(updateRelation.equals("add")){
                student.setSupervisor(thesisSupervisor);
                thesisSupervisor.getStudents().add(student);

                thesisSupervisorRepository.save(thesisSupervisor);
                studentRepository.save(student);

                ThesisSupervisorDetailsDto thesisSupervisorDetailsDto = thesisSupervisorDtoMapper.convertToDetailsDto(thesisSupervisor);
                thesisSupervisorDetailsDto.add(createStudentAddedLinkSelf(updateRelation, tsId,studentIndex).withRel("add operation"));
                return new ResponseEntity(thesisSupervisorDetailsDto,HttpStatus.CREATED);
            } else {
                if (updateRelation.equals("remove")){
                    student.setSupervisor(null);
                    thesisSupervisor.getStudents().remove(student);

                    thesisSupervisorRepository.save(thesisSupervisor);
                    studentRepository.save(student);

                    ThesisSupervisorDetailsDto thesisSupervisorDetailsDto = thesisSupervisorDtoMapper.convertToDetailsDto(thesisSupervisor);
                    thesisSupervisorDetailsDto.add(createStudentAddedLinkSelf(updateRelation, tsId,studentIndex).withRel("remove operation"));

                    return new ResponseEntity(thesisSupervisorDetailsDto,HttpStatus.CREATED);

                } else {
                    return new ResponseEntity(HttpStatus.NOT_FOUND);
                }
            }

        } else {
            return new ResponseEntity(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{tsId}")
    public ResponseEntity deleteThesisSupervisor(@PathVariable Long tsId){
        thesisSupervisorRepository.deleteById(tsId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    private Link createSelfLinkToApi(){
        return linkTo(methodOn(ThesisSupervisorController.class).getSupervisors()).withSelfRel();
    }

    private Link createSelfLink(Long thesisSupervisorId){
        return linkTo(methodOn(ThesisSupervisorController.class).getSupervisorById(thesisSupervisorId)).withSelfRel();
    }

    private Link createStudentLink(Long thesisSupervisorId){
        return linkTo(methodOn(ThesisSupervisorController.class).getStudentsByThesisSupervisor(thesisSupervisorId)).withRel("students");
    }

    private Link createStudentAddedLinkSelf(String updateRelation, Long thesisSupervisorId, Long studentIndex){
        return linkTo(methodOn(ThesisSupervisorController.class).updateThesisSupervisor(thesisSupervisorId,studentIndex,updateRelation)).withSelfRel();
    }
}
