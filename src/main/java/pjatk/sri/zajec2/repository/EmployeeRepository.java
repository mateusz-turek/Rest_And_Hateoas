package pjatk.sri.zajec2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import pjatk.sri.zajec2.model.Employee;

import java.util.List;

@Repository()
public interface EmployeeRepository extends CrudRepository<Employee, Long> {
    List<Employee> findAll();

    @Query("select c.employees from Company as c where c.id=:companyId")
    List<Employee> findEmployeeByEmployerId(@Param("companyId") Long companyId);
}
