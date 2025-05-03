package by.evgen.task_traker_api.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.Set;

@Schema(description = "Информация о пользователе")
public record UserResponse(
        Long id,
        String username,
        Set<String> roles
) { }
