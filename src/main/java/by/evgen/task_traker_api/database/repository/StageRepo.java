package by.evgen.task_traker_api.database.repository;

import by.evgen.task_traker_api.database.entity.Project;
import by.evgen.task_traker_api.database.entity.Stage;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface StageRepo extends JpaRepository<Stage, Long> {
    @Query("SELECT MAX(s.orderNumber) FROM Stage s WHERE s.project.id = :projectId")
    Optional<Long> findMaxOrderNumberByProjectId(@Param("projectId") Long projectId);

    List<Stage> findByProjectIdAndOrderNumberGreaterThanEqual(Long projectId, Long minOrderNumber);
}
