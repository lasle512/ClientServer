// package ru.university.clothes.service;

// import java.time.LocalDateTime;
// import java.time.temporal.ChronoUnit;
// import java.util.List;
// import java.util.Map;
// import java.util.Set;
// import java.util.stream.Collectors;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.http.HttpHeaders;
// import org.springframework.http.ResponseEntity;
// import org.springframework.security.authentication.AnonymousAuthenticationToken;
// import org.springframework.security.authentication.AuthenticationManager;
// import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
// import org.springframework.security.core.Authentication;
// import org.springframework.security.core.context.SecurityContextHolder;
// import org.springframework.stereotype.Service;
// import org.springframework.transaction.annotation.Transactional;

// import lombok.RequiredArgsConstructor;
// import ru.university.clothes.dto.LoginRequest;
// import ru.university.clothes.dto.LoginResponse;
// import ru.university.clothes.dto.UserLoggedDto;
// import ru.university.clothes.jwt.JwtTokenProviderImpl;
// import ru.university.clothes.mapper.UserMapper;
// import ru.university.clothes.model.Role;
// import ru.university.clothes.model.Token;
// import ru.university.clothes.model.TokenRepository;
// import ru.university.clothes.model.User;
// import ru.university.clothes.repository.UserRepository;
// import ru.university.clothes.util.CookieUtil;

// @Service
// @RequiredArgsConstructor
// public class AuthService {
//         @Value("${app.jwt.access_token.duration.minute}")
//         private long accessTokenDurationMinute;
//         @Value("${app.jwt.access_token.duration.second}")
//         private long accessTokenDurationSecond;
//         @Value("${app.jwt.refresh_token.duration.day}")
//         private long refreshTokenDurationDay;
//         @Value("${app.jwt.refresh_token.duration.second}")
//         private long refreshTokenDurationSecond;
//         private final UserRepository userRepository;
//         private final TokenRepository tokenRepository;
//         private final JwtTokenProviderImpl tokenProvider;
//         private final CookieUtil cookieUtil;
//         private final AuthenticationManager authenticationManager;

//         @Transactional
//         public ResponseEntity<LoginResponse> login(LoginRequest loginRequest, String accessToken, String refreshToken) {
//                 Authentication authentication = authenticationManager.authenticate(
//                                 new UsernamePasswordAuthenticationToken(
//                                                 loginRequest.username(), loginRequest.password()));

//                 String username = loginRequest.username();
//                 User user = userRepository.findByUsername(username).orElseThrow(
//                                 () -> new RuntimeException("User not found"));

//                 boolean accessTokenValid = tokenProvider.validateToken(accessToken);
//                 boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

//                 HttpHeaders responseHeaders = new HttpHeaders();
//                 Token newAccessToken;
//                 Token newRefreshToken;

//                 revokeAllTokenOfUser(user);
//                 Set<String> roleNames = user.getRoles().stream()
//                                 .map(Role::getAuthority)
//                                 .collect(Collectors.toSet());
//                 if (!accessTokenValid && !refreshTokenValid) {
//                         newAccessToken = tokenProvider.generateAccessToken(
//                                         Map.of("roles", roleNames), // Передаем коллекцию ролей вместо одной роли
//                                         accessTokenDurationMinute,
//                                         ChronoUnit.MINUTES,
//                                         user);

//                         newRefreshToken = tokenProvider.generateRefreshToken(
//                                         refreshTokenDurationDay,
//                                         ChronoUnit.DAYS,
//                                         user);

//                         newAccessToken.setUser(user);
//                         newRefreshToken.setUser(user);
//                         // save tokens in db
//                         tokenRepository.saveAll(List.of(newAccessToken, newRefreshToken));
//                         addAccessTokenCookie(responseHeaders, newAccessToken);
//                         addRefreshTokenCookie(responseHeaders, newRefreshToken);
//                 }

//                 if (!accessTokenValid && refreshTokenValid) {
//                         newAccessToken = tokenProvider.generateAccessToken(
//                                         Map.of("roles", roleNames),
//                                         accessTokenDurationMinute,
//                                         ChronoUnit.MINUTES,
//                                         user);

//                         addAccessTokenCookie(responseHeaders, newAccessToken);
//                 }

//                 if (accessTokenValid && refreshTokenValid) {
//                         newAccessToken = tokenProvider.generateAccessToken(
//                                         Map.of("roles", roleNames),
//                                         accessTokenDurationMinute,
//                                         ChronoUnit.MINUTES,
//                                         user);

//                         newRefreshToken = tokenProvider.generateRefreshToken(
//                                         refreshTokenDurationDay,
//                                         ChronoUnit.DAYS,
//                                         user);

//                         newAccessToken.setUser(user);
//                         newRefreshToken.setUser(user);
//                         // save tokens in db
//                         tokenRepository.saveAll(List.of(newAccessToken, newRefreshToken));

//                         addAccessTokenCookie(responseHeaders, newAccessToken);
//                         addRefreshTokenCookie(responseHeaders, newRefreshToken);
//                 }

//                 SecurityContextHolder.getContext()
//                                 .setAuthentication(authentication);

//                 String displayRoles = roleNames.toString();
//                 LoginResponse loginResponse = new LoginResponse(true, displayRoles);
//                 return ResponseEntity.ok()
//                                 .headers(responseHeaders).body(loginResponse);
//         }

//         @Transactional
//         public ResponseEntity<LoginResponse> refresh(String refreshToken) {
//                 boolean refreshTokenValid = tokenProvider.validateToken(refreshToken);

//                 if (!refreshTokenValid)
//                         throw new RuntimeException("Refresh token is invalid");

//                 String username = tokenProvider.getUsernameFromToken(refreshToken);
//                 User user = userRepository.findByUsername(username).orElseThrow(
//                                 () -> new RuntimeException("User not found"));

//                 Set<String> roleNames = user.getRoles().stream()
//                                 .map(Role::getAuthority)
//                                 .collect(Collectors.toSet());

//                 Token newAccessToken = tokenProvider.generateAccessToken(
//                                 Map.of("roles", roleNames),
//                                 accessTokenDurationMinute,
//                                 ChronoUnit.MINUTES,
//                                 user);

//                 HttpHeaders responseHeaders = new HttpHeaders();
//                 addAccessTokenCookie(responseHeaders, newAccessToken);

//                 String displayRoles = roleNames.toString();
//                 LoginResponse loginResponse = new LoginResponse(true, displayRoles);

//                 return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
//         }

//         @Transactional
//         public ResponseEntity<LoginResponse> logout(String accessToken, String refreshToken) {
//                 SecurityContextHolder.clearContext();

//                 String username = tokenProvider.getUsernameFromToken(accessToken);
//                 User user = userRepository.findByUsername(username).orElseThrow(
//                                 () -> new RuntimeException("User not found"));

//                 revokeAllTokenOfUser(user);

//                 HttpHeaders responseHeaders = new HttpHeaders();

//                 responseHeaders.add(HttpHeaders.SET_COOKIE,
//                                 cookieUtil.deleteAccessTokenCookie().toString());
//                 responseHeaders.add(HttpHeaders.SET_COOKIE,
//                                 cookieUtil.deleteRefreshTokenCookie().toString());

//                 LoginResponse loginResponse = new LoginResponse(false, "Logged out successfully");

//                 return ResponseEntity.ok().headers(responseHeaders).body(loginResponse);
//         }

//         @Transactional
//         public UserLoggedDto getUserLoggedInfo() {
//                 Authentication authentication = SecurityContextHolder.getContext()
//                                 .getAuthentication();
//                 if (authentication instanceof AnonymousAuthenticationToken)
//                         throw new RuntimeException("No user authenticated");

//                 String username = authentication.getName();

//                 User user = userRepository.findByUsername(username).orElseThrow(
//                                 () -> new RuntimeException("User not found"));

//                 return UserMapper.userToUserLoggedDto(user);
//         }

//         private void addAccessTokenCookie(HttpHeaders httpHeaders, Token token) {
//                 httpHeaders.add(HttpHeaders.SET_COOKIE,
//                                 cookieUtil.createAccessTokenCookie(token.getValue(), accessTokenDurationSecond)
//                                                 .toString());
//         }

//         private void addRefreshTokenCookie(HttpHeaders httpHeaders, Token token) {
//                 httpHeaders.add(HttpHeaders.SET_COOKIE,
//                                 cookieUtil.createRefreshTokenCookie(token.getValue(), refreshTokenDurationSecond)
//                                                 .toString());
//         }

//         private void revokeAllTokenOfUser(User user) {
//                 // get all user tokens
//                 Set<Token> tokens = user.getTokens();

//                 tokens.forEach(token -> {
//                         if (token.getExpiryDate().isBefore(LocalDateTime.now()))
//                                 tokenRepository.delete(token);
//                         else if (!token.isDisabled()) {
//                                 token.setDisabled(true);
//                                 tokenRepository.save(token);
//                         }
//                 });
//         }
// }