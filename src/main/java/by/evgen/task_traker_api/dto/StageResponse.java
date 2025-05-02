package by.evgen.task_traker_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.Instant;
import java.util.List;

public record StageResponse(
        Long id,
        String name,
        @JsonProperty("created_at") Instant createdAt,
        @JsonProperty("order_number") Long orderNumber,
        List<TaskResponse> tasks
) { }
