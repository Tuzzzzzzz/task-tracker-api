package by.evgen.task_traker_api.service.security;

import by.evgen.task_traker_api.database.entity.security.RefreshToken;
import by.evgen.task_traker_api.database.repository.RefreshTokenRepo;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.time.Instant;
import java.util.HexFormat;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class RefreshTokenService {
    @Value("${security.jwt.refresh_token.expiration}")
    private Duration refreshTokenExpiration;

    @Value("${security.ip.salt}")
    private String ipSalt;

    private final RefreshTokenRepo refreshTokenRepo;

    @Transactional
    public String generateToken(Long userId, HttpServletRequest request) {

        RefreshToken refreshToken = RefreshToken.builder()
                .userId(userId)
                .token(UUID.randomUUID().toString())
                .expiryDate(Instant.now().plus(refreshTokenExpiration))
                .deviceInfo(request.getHeader("User-Agent"))
                .ip(hashIp(request.getRemoteAddr()))
                .build();

        refreshTokenRepo.save(refreshToken);
        return refreshToken.getToken();
    }

    private String hashIp(String ip) {
        MessageDigest digest = null;
        try {
            digest = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
        byte[] hash = digest.digest((ip + ipSalt).getBytes());
        return HexFormat.of().formatHex(hash);
    }

    public boolean isValid(String token, Long userId) {
        return refreshTokenRepo.existsByUserIdAndTokenAndExpiryDateAfter(userId, token, Instant.now());
    }

    @Transactional
    public void deleteAllByUserId(Long userId) {
        refreshTokenRepo.deleteAllByUserId(userId);
    }

    @Transactional
    public void delete(String token, HttpServletRequest request){
        refreshTokenRepo.deleteByTokenAndDeviceInfoAndIp(
                token,
                request.getHeader("User-Agent"),
                hashIp(request.getRemoteAddr())
        );
    }
}
