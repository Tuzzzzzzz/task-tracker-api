package by.evgen.task_traker_api.service.security;

import by.evgen.task_traker_api.database.entity.RoleEnum;
import by.evgen.task_traker_api.database.entity.User;
import by.evgen.task_traker_api.database.entity.security.RefreshToken;
import by.evgen.task_traker_api.database.repository.RefreshTokenRepo;
import by.evgen.task_traker_api.database.repository.RoleRepo;
import by.evgen.task_traker_api.database.repository.UserRepo;
import by.evgen.task_traker_api.dto.security.*;
import by.evgen.task_traker_api.exception.InvalidRefreshTokenException;
import by.evgen.task_traker_api.exception.NonUniquePasswordException;
import by.evgen.task_traker_api.exception.UserNotFoundException;
import by.evgen.task_traker_api.mapper.UserMapper;
import by.evgen.task_traker_api.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserAuthenticationService {
    private final UserService userService;
    private final UserRepo userRepo;
    private final UserMapper userMapper;
    private final RoleRepo roleRepo;
    private final JwtTokenService jwtTokenService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final RefreshTokenService refreshTokenService;
    private final RefreshTokenRepo refreshTokenRepo;

    public UserResponse signUp(
            HttpServletRequest httpServletRequest,
            SignUpRequest request
    ) {
        User user = User.builder()
                .username(request.username())
                .password(passwordEncoder.encode(request.password()))
                .roles(Set.of(roleRepo.findByRole(RoleEnum.ROLE_USER).get()))
                .build();

        return userService.create(user);
    }


    public AccessRefreshTokenPair signIn(
            HttpServletRequest httpServletRequest,
            SignInRequest request
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.username(),
                        request.password()
                )
        );

        UserResponse userResponse = userService.findByUsername(request.username());

        refreshTokenService.deleteAllByUserId(userResponse.id());

        return new AccessRefreshTokenPair(
                jwtTokenService.generateAccessToken(userResponse),
                refreshTokenService.generateToken(userResponse.id(), httpServletRequest)
        );
    }

    public AccessRefreshTokenPair refreshTokens(
            HttpServletRequest httpServletRequest,
            String refreshToken
    ) {
        Long userId = refreshTokenRepo.findByToken(refreshToken)
                .map(RefreshToken::getUserId)
                .orElseThrow(() -> new InvalidRefreshTokenException(refreshToken));

        if(!refreshTokenService.isValid(refreshToken, userId)){
            throw new InvalidRefreshTokenException(refreshToken);
        }

        refreshTokenService.delete(refreshToken, httpServletRequest);

        UserResponse userResponse = userService.findById(userId);

        return new AccessRefreshTokenPair(
                jwtTokenService.generateAccessToken(userResponse),
                refreshTokenService.generateToken(userResponse.id(), httpServletRequest)
        );
    }


    @Transactional
    public AccessRefreshTokenPair updatePassword(
            UpdatePasswordRequest request,
            HttpServletRequest httpServletRequest,
            String accessToken
    ) {
        Long userId = jwtTokenService.extractUserId(accessToken);

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        String oldPassword = user.getPassword();
        String newPassword = request.newPassword();

        if (!passwordEncoder.matches(newPassword, oldPassword)) {
            throw new NonUniquePasswordException(newPassword);
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        userRepo.save(user);

        refreshTokenService.deleteAllByUserId(user.getId());

        return new AccessRefreshTokenPair(
                jwtTokenService.generateAccessToken(userMapper.toResponse(user)),
                refreshTokenService.generateToken(user.getId(), httpServletRequest)
        );
    }
}