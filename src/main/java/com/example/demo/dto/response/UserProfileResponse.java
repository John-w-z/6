package com.example.demo.dto.response;

import java.time.LocalDate;

public class UserProfileResponse {

    private Long id;
    private Long userId;
    private String phone;
    private String address;
    private LocalDate birthDate;
    private String avatarUrl;
    private String bio;

    public UserProfileResponse() {
    }

    public UserProfileResponse(Long id, Long userId, String phone, String address,
                               LocalDate birthDate, String avatarUrl, String bio) {
        this.id = id;
        this.userId = userId;
        this.phone = phone;
        this.address = address;
        this.birthDate = birthDate;
        this.avatarUrl = avatarUrl;
        this.bio = bio;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

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