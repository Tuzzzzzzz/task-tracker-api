package by.evgen.task_traker_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public record ProjectResponse(
        Long id,
        String name,
        String description,
        @JsonProperty("created_at") Instant createdAt,
        List<StageResponse> stages
) { }
