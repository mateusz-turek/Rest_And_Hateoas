package pjatk.sri.zajec2.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import pjatk.sri.zajec2.model.Company;

import java.util.List;
import java.util.Optional;

public interface CompanyRepository extends CrudRepository<Company,Long> {

    List<Company> findAll();

    @Query("from Company c left join fetch c.employees where c.id=:companyId")
    Optional<Company> findById(@Param("companyId") Long companyId);
}
