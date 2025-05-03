package by.evgen.task_traker_api.controller;

import by.evgen.task_traker_api.dto.security.*;
import by.evgen.task_traker_api.service.UserService;
import by.evgen.task_traker_api.service.security.TokenCookieService;
import by.evgen.task_traker_api.service.security.UserAuthenticationService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;


@RequiredArgsConstructor
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final TokenCookieService tokenCookieService;
    private final UserAuthenticationService userAuthenticationService;
    private final UserService userService;


    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signUp(
            @RequestBody @Valid SignUpRequest request,
            HttpServletRequest httpServletRequest
    ) {
        UserResponse userResponse = userAuthenticationService.signUp(httpServletRequest, request);
        return ResponseEntity.status(HttpStatus.CREATED).body(userResponse);
    }


    @PostMapping("/signin")
    public ResponseEntity<Void> signUp(
            @RequestBody @Valid SignInRequest request,
            HttpServletRequest httpServletRequest,
            HttpServletResponse response
    ) {
        AccessRefreshTokenPair tokens = userAuthenticationService.signIn(httpServletRequest, request);

        response.addHeader(HttpHeaders.SET_COOKIE,
                tokenCookieService.createAccessCookie(tokens.accessToken()).toString());
        response.addHeader(HttpHeaders.SET_COOKIE,
                tokenCookieService.createRefreshCookie(tokens.refreshToken()).toString());

        return ResponseEntity.ok().build();
    }

    @PostMapping("/signout")
    public ResponseEntity<Void> signOut(HttpServletResponse response){
        response.addHeader(HttpHeaders.SET_COOKIE,
                tokenCookieService.createExpiredAccessCookie().toString());
        response.addHeader(HttpHeaders.SET_COOKIE,
                tokenCookieService.createExpiredRefreshCookie().toString());

        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getCurrentUser(
            @AuthenticationPrincipal UserDetails userDetails
    ) {
        UserResponse user = userService.findByUsername(userDetails.getUsername());
        return ResponseEntity.ok(user);
    }

    @PostMapping("/refresh")
    public ResponseEntity<Void> refreshTokens(
            @CookieValue(name = "accessToken") String token,
            HttpServletRequest httpServletRequest,
            HttpServletResponse response
    ) {
        AccessRefreshTokenPair tokens = userAuthenticationService.refreshTokens(httpServletRequest, token);

        response.addHeader(HttpHeaders.SET_COOKIE,
                tokenCookieService.createAccessCookie(tokens.accessToken()).toString());
        response.addHeader(HttpHeaders.SET_COOKIE,
                tokenCookieService.createRefreshCookie(tokens.refreshToken()).toString());

        return ResponseEntity.noContent().build();
    }

    @PutMapping("/password")
    public ResponseEntity<Void> changePassword(
            @RequestBody UpdatePasswordRequest request,
            HttpServletRequest httpServletRequest,
            @CookieValue(name = "accessToken") String token,
            HttpServletResponse response
    ){
        AccessRefreshTokenPair tokens = userAuthenticationService.updatePassword(request, httpServletRequest, token);

        response.addHeader(HttpHeaders.SET_COOKIE,
                tokenCookieService.createAccessCookie(tokens.accessToken()).toString());
        response.addHeader(HttpHeaders.SET_COOKIE,
                tokenCookieService.createRefreshCookie(tokens.refreshToken()).toString());

        return ResponseEntity.noContent().build();
    }
}
