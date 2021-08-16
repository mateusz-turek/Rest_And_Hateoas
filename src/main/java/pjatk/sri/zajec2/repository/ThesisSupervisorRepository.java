package pjatk.sri.zajec2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pjatk.sri.zajec2.model.ThesisSupervisor;

import java.util.List;
import java.util.Optional;

public interface ThesisSupervisorRepository extends CrudRepository<ThesisSupervisor,Long> {

    List<ThesisSupervisor> findAll();

    @Query("from ThesisSupervisor ts left join fetch ts.students where ts.id=:supervisorId")
    Optional<ThesisSupervisor> findById(@Param("supervisorId") Long supervisorId);
}
