package by.evgen.task_traker_api;

import by.evgen.task_traker_api.database.entity.Role;
import by.evgen.task_traker_api.database.entity.RoleEnum;
import by.evgen.task_traker_api.database.entity.User;
import by.evgen.task_traker_api.database.repository.RoleRepo;
import by.evgen.task_traker_api.database.repository.UserRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Component
@RequiredArgsConstructor
public class DataInitializer {
    private final RoleRepo roleRepository;
    private final UserRepo userRepository;
    private final PasswordEncoder passwordEncoder;

    @PostConstruct
    @Transactional
    public void init() {
        initRoles();
        initAdmin();
    }

    private void initRoles() {
        for (RoleEnum roleEnum : RoleEnum.values()) {
            if (!roleRepository.existsByRole(roleEnum)) {
                roleRepository.save(Role.builder()
                        .role(roleEnum)
                        .build());
            }
        }
    }

    private void initAdmin() {
        String username = "admin";
        if (!userRepository.existsByUsername(username)) {
            Role adminRole = roleRepository.findByRole(RoleEnum.ROLE_ADMIN)
                    .orElseThrow(() -> new IllegalStateException("ROLE_ADMIN not found"));

            User admin = User.builder()
                    .username(username)
                    .password(passwordEncoder.encode("admin"))
                    .roles(Set.of(adminRole))
                    .build();

            userRepository.save(admin);
        }
    }
}
