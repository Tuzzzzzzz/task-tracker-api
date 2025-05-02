package by.evgen.task_traker_api.database.repository;


import by.evgen.task_traker_api.database.entity.Role;
import by.evgen.task_traker_api.database.entity.RoleEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepo extends JpaRepository<Role, Long> {
    Optional<Role> findByRole(RoleEnum roleEnum);

    boolean existsByRole(RoleEnum roleEnum);
}
