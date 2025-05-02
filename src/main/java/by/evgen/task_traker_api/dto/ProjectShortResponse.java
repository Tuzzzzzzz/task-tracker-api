package by.evgen.task_traker_api.dto;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;

public record ProjectShortResponse(
        Long id,
        String name,
        String description,
        @JsonProperty("created_at") Instant createdAt
) { }
