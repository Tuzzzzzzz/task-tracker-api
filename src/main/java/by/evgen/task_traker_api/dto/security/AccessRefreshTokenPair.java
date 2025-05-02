package by.evgen.task_traker_api.dto.security;

public record AccessRefreshTokenPair(
        String accessToken,
        String refreshToken
) { }
