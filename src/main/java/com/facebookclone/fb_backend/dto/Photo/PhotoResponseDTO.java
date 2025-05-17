package com.facebookclone.fb_backend.dto.Photo;

import java.time.LocalDateTime;

public class PhotoResponseDTO {
    private Long id;
    private String name;
    private String imageUrl;
    private Long albumId;
    private LocalDateTime createdAt;

    public PhotoResponseDTO() {
    }

    public PhotoResponseDTO(Long id, String name, String imageUrl, Long albumId, LocalDateTime createdAt) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.albumId = albumId;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
