package by.evgen.task_traker_api.dto.security;

import jakarta.validation.constraints.NotBlank;

public record RefreshRequest(
        @NotBlank
        String username
) { }
