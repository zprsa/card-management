package hr.task.cardmanagement.repository;

import hr.task.cardmanagement.model.CardFile;
import org.springframework.data.jdbc.repository.query.Modifying;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional
public interface CardFileRepository extends CrudRepository<CardFile, Long> {

    @Query("select c.* from card_file c")
    List<CardFile> findAll();

    @Query("select c.* from card_file c where c.oib = :oib and c.status = :status")
    Optional<CardFile> findByOibAndStatus(@Param("oib") String oib, @Param("status") String status);

    @Modifying
    @Query("update card_file set status = :status, modified_at = :modifiedAt where oib = :oib")
    void updateStatus(@Param("oib") String oib,
                      @Param("status") String status,
                      @Param("modifiedAt") LocalDateTime modifiedAt);
}
