package com.example.demo.dto.request;

import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class UserProfileRequest {

    @Pattern(regexp = "^[+]?[0-9\\s-]{7,20}$",
             message = "El teléfono no tiene un formato válido")
    private String phone;

    @Size(max = 255, message = "La dirección no puede superar los 255 caracteres")
    private String address;

    @Past(message = "La fecha de nacimiento debe ser anterior a hoy")
    private LocalDate birthDate;

    @Size(max = 500, message = "La URL del avatar no puede superar los 500 caracteres")
    private String avatarUrl;

    @Size(max = 1000, message = "La biografía no puede superar los 1000 caracteres")
    private String bio;

    public UserProfileRequest() {
    }

    public UserProfileRequest(String phone, String address, LocalDate birthDate,
                              String avatarUrl, String bio) {
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
    }

    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public LocalDate getBirthDate() { return birthDate; }
    public void setBirthDate(LocalDate birthDate) { this.birthDate = birthDate; }

    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }

    public String getBio() { return bio; }
    public void setBio(String bio) { this.bio = bio; }
}