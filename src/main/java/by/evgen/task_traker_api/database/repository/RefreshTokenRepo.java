package by.evgen.task_traker_api.database.repository;

import by.evgen.task_traker_api.database.entity.security.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;

public interface RefreshTokenRepo extends JpaRepository<RefreshToken, Long> {

    boolean existsByUserIdAndTokenAndExpiryDateAfter(Long userId, String token, Instant now);

    void deleteAllByUserId(Long userId);

    void deleteByTokenAndDeviceInfoAndIp(String token, String deviceInfo, String ip);

    Optional<RefreshToken> findByToken(String token);
}
