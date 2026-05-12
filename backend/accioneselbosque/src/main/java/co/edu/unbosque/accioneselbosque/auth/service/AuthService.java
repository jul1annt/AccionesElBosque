package co.edu.unbosque.accioneselbosque.auth.service;

import co.edu.unbosque.accioneselbosque.auth.adapter.GoogleOAuthAdapter;
import co.edu.unbosque.accioneselbosque.auth.adapter.GoogleOAuthException;
import co.edu.unbosque.accioneselbosque.auth.dto.AuthResponseDto;
import co.edu.unbosque.accioneselbosque.auth.dto.GoogleTokenResponseDto;
import co.edu.unbosque.accioneselbosque.auth.dto.GoogleUserInfoDto;
import co.edu.unbosque.accioneselbosque.auth.model.User;
import co.edu.unbosque.accioneselbosque.auth.repository.UserRepository;
import co.edu.unbosque.accioneselbosque.auth.util.JwtUtil;
import co.edu.unbosque.accioneselbosque.notificaciones.exceptions.EmailDuplicadoException;
import co.edu.unbosque.accioneselbosque.notificaciones.service.EmailService;

import java.util.Optional;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthService {

    private final GoogleOAuthAdapter googleOAuthAdapter;
    private final UserRepository userRepository;
    private final JwtUtil jwtUtil;
    private final EmailService emailService;

    public AuthService(GoogleOAuthAdapter googleOAuthAdapter,
                       UserRepository userRepository,
                       JwtUtil jwtUtil,
                       EmailService emailService) {
        this.googleOAuthAdapter = googleOAuthAdapter;
        this.userRepository = userRepository;
        this.jwtUtil = jwtUtil;
        this.emailService = emailService;
    }

    @Transactional
    public AuthResponseDto registerWithGoogle(String authorizationCode) {
        GoogleTokenResponseDto tokenResponse = googleOAuthAdapter.exchangeCodeForToken(authorizationCode);
        GoogleUserInfoDto googleUser = googleOAuthAdapter.getUserInfo(tokenResponse.getAccessToken());

        validateGoogleUser(googleUser);

        Optional<User> existingUser = userRepository.findByGoogleId(googleUser.getSub());

        boolean isNewUser = existingUser.isEmpty();
        User user = isNewUser
            ? createUser(googleUser)
            : existingUser.get();

        if (isNewUser) {
            emailService.enviarBienvenida(user.getEmail(), user.getName());
        }

        String jwt = jwtUtil.generateToken(user);

        return AuthResponseDto.builder()
            .token(jwt)
            .userId(user.getId())
            .email(user.getEmail())
            .name(user.getName())
            .pictureUrl(user.getPictureUrl())
            .isNewUser(isNewUser)
            .build();
    }

    private void validateGoogleUser(GoogleUserInfoDto googleUser) {
        if (googleUser.getEmail() == null || googleUser.getEmail().isBlank()) {
            throw new GoogleOAuthException("No se pudo obtener el email del perfil de Google");
        }
        if (Boolean.FALSE.equals(googleUser.getEmailVerified())) {
            throw new GoogleOAuthException("El email de Google no está verificado");
        }
    }

    private User createUser(GoogleUserInfoDto googleUser) {
        if (userRepository.existsByEmail(googleUser.getEmail())) {
            throw new EmailDuplicadoException(googleUser.getEmail());
        }

        User user = new User();
        user.setGoogleId(googleUser.getSub());
        user.setEmail(googleUser.getEmail());
        user.setName(googleUser.getName());
        user.setPictureUrl(googleUser.getPictureUrl());
        user.setProvider(User.AuthProvider.GOOGLE);

        return userRepository.save(user);
    }
}