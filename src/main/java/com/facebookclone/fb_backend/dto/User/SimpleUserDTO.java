package com.facebookclone.fb_backend.dto.User;

public class SimpleUserDTO {
    private long id;
    private String name;
    private String email;

    // Constructors and Getters
    public SimpleUserDTO(){}
    public SimpleUserDTO(long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public long getId() { return id; }

    public String getName() { return name; }

    public String getEmail() { return email; }
}
