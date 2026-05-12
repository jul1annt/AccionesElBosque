package co.edu.unbosque.accioneselbosque.auth.dto;

import java.util.UUID;

public class AuthResponseDto {

    private String token;
    private UUID userId;
    private String email;
    private String name;
    private String pictureUrl;
    private boolean isNewUser;

    private AuthResponseDto() {}

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        private final AuthResponseDto dto = new AuthResponseDto();

        public Builder token(String token) {
            dto.token = token;
            return this;
        }

        public Builder userId(UUID userId) {
            dto.userId = userId;
            return this;
        }

        public Builder email(String email) {
            dto.email = email;
            return this;
        }

        public Builder name(String name) {
            dto.name = name;
            return this;
        }

        public Builder pictureUrl(String pictureUrl) {
            dto.pictureUrl = pictureUrl;
            return this;
        }

        public Builder isNewUser(boolean isNewUser) {
            dto.isNewUser = isNewUser;
            return this;
        }

        public AuthResponseDto build() {
            return dto;
        }
    }

    public String getToken() {
        return token;
    }

    public UUID getUserId() {
        return userId;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public boolean isNewUser() {
        return isNewUser;
    }
}
