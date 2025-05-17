package com.facebookclone.fb_backend.dto.Album;

import com.facebookclone.fb_backend.dto.Photo.SimplePhotoDTO;

import java.time.LocalDateTime;
import java.util.List;

public class SimpleAlbumDTO {
    private long id;
    private String name;
    private LocalDateTime createdAt;
    private List<SimplePhotoDTO> photos;

    public SimpleAlbumDTO(){}

    public SimpleAlbumDTO(long id, String name, LocalDateTime createdAt, List<SimplePhotoDTO> photos) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.photos = photos;
    }

    public long getId() { return this.id; }
    public void setId(long id) { this.id = id; }

    public String getName() { return this.name; }
    public void setName(String name) { this.name = name; }

    public LocalDateTime getCreatedAt() { return this.createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<SimplePhotoDTO> getPhotos() { return this.photos; }
    public void setPhotos(List<SimplePhotoDTO> photos) { this.photos = photos; }
}
