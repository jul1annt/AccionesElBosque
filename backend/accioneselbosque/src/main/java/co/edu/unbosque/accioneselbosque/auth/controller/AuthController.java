package co.edu.unbosque.accioneselbosque.auth.controller;

import co.edu.unbosque.accioneselbosque.auth.dto.AuthResponseDto;
import co.edu.unbosque.accioneselbosque.auth.dto.GoogleAuthCodeRequest;
import co.edu.unbosque.accioneselbosque.auth.service.AuthService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/google/register")
    public ResponseEntity<AuthResponseDto> registerWithGoogle(
        @Valid @RequestBody GoogleAuthCodeRequest request
    ) {
        AuthResponseDto response = authService.registerWithGoogle(
            request.getCode()
        );
        return ResponseEntity.ok(response);
    }
}
