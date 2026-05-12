package co.edu.unbosque.accioneselbosque.auth.adapter;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import co.edu.unbosque.accioneselbosque.auth.dto.GoogleTokenResponseDto;
import co.edu.unbosque.accioneselbosque.auth.dto.GoogleUserInfoDto;

@Component
public class GoogleOAuthAdapter {

    private final RestTemplate restTemplate;

    @Value("${app.google.client-id}")
    private String clientId;

    @Value("${app.google.client-secret}")
    private String clientSecret;

    @Value("${app.google.redirect-uri}")
    private String redirectUri;

    @Value("${app.google.token-url}")
    private String tokenUrl;

    @Value("${app.google.userinfo-url}")
    private String userInfoUrl;

    public GoogleOAuthAdapter(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public GoogleTokenResponseDto exchangeCodeForToken(String authorizationCode) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("code", authorizationCode);
        params.add("client_id", clientId);
        params.add("client_secret", clientSecret);
        params.add("redirect_uri", redirectUri);
        params.add("grant_type", "authorization_code");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        try {
            ResponseEntity<GoogleTokenResponseDto> response = restTemplate.postForEntity(
                tokenUrl,
                request,
                GoogleTokenResponseDto.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new GoogleOAuthException("Respuesta inválida al intercambiar el código de autorización");
            }

            return response.getBody();

        } catch (RestClientException e) {
            throw new GoogleOAuthException("Error de conexión con Google al intercambiar el código: " + e.getMessage());
        }
    }

    public GoogleUserInfoDto getUserInfo(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<Void> request = new HttpEntity<>(headers);

        try {
            ResponseEntity<GoogleUserInfoDto> response = restTemplate.exchange(
                userInfoUrl,
                HttpMethod.GET,
                request,
                GoogleUserInfoDto.class
            );

            if (!response.getStatusCode().is2xxSuccessful() || response.getBody() == null) {
                throw new GoogleOAuthException("Respuesta inválida al obtener el perfil de Google");
            }

            return response.getBody();

        } catch (RestClientException e) {
            throw new GoogleOAuthException("Error de conexión con Google al obtener el perfil: " + e.getMessage());
        }
    }
}
