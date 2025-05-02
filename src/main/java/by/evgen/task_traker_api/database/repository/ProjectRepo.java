package by.evgen.task_traker_api.database.repository;

import by.evgen.task_traker_api.database.entity.Project;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProjectRepo extends JpaRepository<Project, Long> {
   List<Project> findAllByUserId(Long id);

   @EntityGraph(attributePaths = "stages")
   Optional<Project> findById(Long id);
}
