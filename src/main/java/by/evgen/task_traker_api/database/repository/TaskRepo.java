package by.evgen.task_traker_api.database.repository;

import by.evgen.task_traker_api.database.entity.Project;
import by.evgen.task_traker_api.database.entity.Stage;
import by.evgen.task_traker_api.database.entity.Task;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TaskRepo extends JpaRepository<Task, Long> {
}
