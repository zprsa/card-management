package hr.task.cardmanagement.repository;

import hr.task.cardmanagement.model.Person;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public interface PersonRepository extends CrudRepository<Person, Long> {

    @Query("select p.* from person p")
    List<Person> findAll();

    @Query("select p.* from person p where p.oib = :oib")
    Optional<Person> findByOib(@Param("oib") String oib);
}
