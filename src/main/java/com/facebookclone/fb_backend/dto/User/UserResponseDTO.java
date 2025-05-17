package com.facebookclone.fb_backend.dto.User;

import com.facebookclone.fb_backend.dto.Album.SimpleAlbumDTO;

import java.util.List;

public class UserResponseDTO {
    private long id;
    private String ownerName;
    private List<SimpleAlbumDTO> albums;

    public UserResponseDTO() {}

    public UserResponseDTO(long id, String ownerName, List<SimpleAlbumDTO> albums) {
        this.id = id;
        this.ownerName = ownerName;
        this.albums = albums;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getOwnerName() { return ownerName; }
    public void setOwnerName(String ownerName) { this.ownerName = ownerName; }

    public List<SimpleAlbumDTO> getAlbums() { return albums; }
    public void setAlbums(List<SimpleAlbumDTO> albums) { this.albums = albums; }
}
