package co.edu.unbosque.accioneselbosque.auth.dto;

import jakarta.validation.constraints.NotBlank;

public class GoogleAuthCodeRequest {

    @NotBlank(message = "El código de autorización es requerido")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
