package by.evgen.task_traker_api.dto.security;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdatePasswordRequest(
        @NotBlank(message = "Пароль не должен быть пустым")
        @JsonProperty("new_password") String newPassword
) { }
