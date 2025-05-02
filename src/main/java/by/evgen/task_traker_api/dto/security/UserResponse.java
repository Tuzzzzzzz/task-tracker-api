package by.evgen.task_traker_api.dto.security;

import java.util.Set;

public record UserResponse(
        Long id,
        String username,
        Set<String> roles
) { }
