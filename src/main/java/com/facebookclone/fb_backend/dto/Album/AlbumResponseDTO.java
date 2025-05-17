package com.facebookclone.fb_backend.dto.Album;

import com.facebookclone.fb_backend.dto.Photo.SimplePhotoDTO;
import com.facebookclone.fb_backend.dto.User.SimpleUserDTO;

import java.time.LocalDateTime;
import java.util.List;

public class AlbumResponseDTO {
    private long id;
    private String name;
    private LocalDateTime createdAt;
    private SimpleUserDTO owner;
    private List<SimplePhotoDTO> photos;

    // Constructors and Getters

    public AlbumResponseDTO() {}

    public AlbumResponseDTO(long id, String name, LocalDateTime createdAt, List<SimplePhotoDTO> photos){
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.photos = photos;
    }

    public AlbumResponseDTO(long id, String name,LocalDateTime createdAt, SimpleUserDTO owner) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.owner = owner;
    }

    public long getId() { return id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return name;}
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public SimpleUserDTO getOwner() { return owner; }
    public void setOwner(SimpleUserDTO owner) { this.owner = owner; }

    public List<SimplePhotoDTO> getPhotos() { return this.photos; }
    public void setPhotos(List<SimplePhotoDTO> photos) { this.photos = photos; }
}
