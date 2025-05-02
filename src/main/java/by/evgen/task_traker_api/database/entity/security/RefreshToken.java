package by.evgen.task_traker_api.database.entity.security;

import by.evgen.task_traker_api.database.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Setter
@Getter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String token;

    @Column(name = "device_info", nullable = false)
    private String deviceInfo;

    @Column(nullable = false)
    private String ip;

    @Column(name = "expiry_date", nullable = false)
    private Instant expiryDate;

    @Column(name = "user_id", nullable = false)
    private Long userId;
}
