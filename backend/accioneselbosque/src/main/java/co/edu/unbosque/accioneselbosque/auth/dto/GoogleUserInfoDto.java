package co.edu.unbosque.accioneselbosque.auth.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GoogleUserInfoDto {

    private String sub;
    private String email;
    private String name;

    @JsonProperty("picture")
    private String pictureUrl;

    @JsonProperty("email_verified")
    private Boolean emailVerified;

    public String getSub() {
        return sub;
    }

    public void setSub(String sub) {
        this.sub = sub;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }

    public Boolean getEmailVerified() {
        return emailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        this.emailVerified = emailVerified;
    }
}
