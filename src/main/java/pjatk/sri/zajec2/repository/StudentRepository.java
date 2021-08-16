package pjatk.sri.zajec2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pjatk.sri.zajec2.model.Student;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRepository extends CrudRepository<Student, Long> {

    Optional<Student> findByIndexNumber(Long aLong);

    List<Student> findAll();

    @Query("select ts.students from ThesisSupervisor as ts where ts.id=:supervisorId")
    List<Student> findStudentsBySupervisorId(@Param("supervisorId") Long supervisorId);
}
