package by.evgen.task_traker_api.dto.security;

import jakarta.validation.constraints.NotBlank;

public record SignInRequest(
        @NotBlank(message = "Username не может быть пустым")
        String username,

        @NotBlank(message = "Пароль не должен быть пустым")
        String password
) { }
