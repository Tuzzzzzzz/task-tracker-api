package by.evgen.task_traker_api.dto.security;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на выдачу нового access токена (refresh токен тоже будет обновлён)")
public record RefreshRequest(
        @NotBlank(message = "Username не может быть пустым")
        String username
) { }
