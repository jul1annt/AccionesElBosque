package co.edu.unbosque.accioneselbosque.auth.exceptions;

import co.edu.unbosque.accioneselbosque.auth.adapter.GoogleOAuthException;
import co.edu.unbosque.accioneselbosque.notificaciones.exceptions.EmailDuplicadoException;

import java.time.LocalDateTime;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailDuplicadoException.class)
    public ResponseEntity<Map<String, Object>> handleEmailDuplicado(EmailDuplicadoException e) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(Map.of(
                "timestamp", LocalDateTime.now().toString(),
                "status", 409,
                "error", "Email duplicado",
                "message", e.getMessage()));
    }

    @ExceptionHandler(GoogleOAuthException.class)
    public ResponseEntity<Map<String, Object>> handleGoogleOAuthException(
            GoogleOAuthException e) {
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(
                Map.of(
                        "timestamp",
                        LocalDateTime.now().toString(),
                        "status",
                        502,
                        "error",
                        "Google OAuth Error",
                        "message",
                        e.getMessage()));
    }
}
