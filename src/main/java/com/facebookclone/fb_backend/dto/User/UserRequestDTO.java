package com.facebookclone.fb_backend.dto.User;

public class UserRequestDTO {
    private String name;
    private String email;
    private String password;

    // Constructors and  Getters
    public UserRequestDTO(){}

    public UserRequestDTO(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public String getEmail() { return email; }

    public String getPassword() {
        return password;
    }
}
