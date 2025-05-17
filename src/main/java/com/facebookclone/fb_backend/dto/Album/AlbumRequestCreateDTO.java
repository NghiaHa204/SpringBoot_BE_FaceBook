package com.facebookclone.fb_backend.dto.Album;

public class AlbumRequestCreateDTO {
    private String name;
    private Long ownerId;

    // Constructors and  Getters
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public Long getOwnerId() { return ownerId; }
    public void setOwnerId(Long ownerId) { this.ownerId = ownerId; }

}
