package by.evgen.task_traker_api.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Запрос на вход")
public record SignInRequest(
        @Schema(description = "Username", example = "user123")
        @NotBlank(message = "Username не может быть пустым")
        String username,

        @Schema(description = "Пароль", example = "Passw0rd")
        @NotBlank(message = "Пароль не должен быть пустым")
        String password
) { }
