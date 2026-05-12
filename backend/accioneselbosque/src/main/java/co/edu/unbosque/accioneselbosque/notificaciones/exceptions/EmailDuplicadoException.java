package co.edu.unbosque.accioneselbosque.notificaciones.exceptions;

public class EmailDuplicadoException extends RuntimeException {

    public EmailDuplicadoException(String email) {
        super("El email " + email + " ya tiene una cuenta registrada. Puedes iniciar sesión directamente.");
    }
}