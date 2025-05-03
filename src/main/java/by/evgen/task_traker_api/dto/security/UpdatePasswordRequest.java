package by.evgen.task_traker_api.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Запрос на изменения пароля")
public record UpdatePasswordRequest(
        @NotBlank(message = "Пароль не должен быть пустым")
        @JsonProperty("new_password") String newPassword
) { }
