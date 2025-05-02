package by.evgen.task_traker_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record TaskResponse(
        Long id,
        String name,
        @JsonProperty("created_at") Instant createdAt,
        String description
) { }
