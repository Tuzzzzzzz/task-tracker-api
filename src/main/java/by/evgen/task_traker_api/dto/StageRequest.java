package by.evgen.task_traker_api.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record StageRequest(
        String name,
        @JsonProperty("order_number") Long orderNumber
) { }
