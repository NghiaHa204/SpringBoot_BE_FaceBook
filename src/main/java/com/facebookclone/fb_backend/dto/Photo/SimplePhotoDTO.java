package com.facebookclone.fb_backend.dto.Photo;

import java.time.LocalDateTime;

public class SimplePhotoDTO {
    private long id;
    private String name;
    private String imageUrl;
    private LocalDateTime createdAt;

    public SimplePhotoDTO() {}
    public SimplePhotoDTO(long id, String name, String imageUrl, LocalDateTime createdAt){
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.createdAt = createdAt;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
