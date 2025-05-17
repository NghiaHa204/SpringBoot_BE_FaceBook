package com.facebookclone.fb_backend.dto.Photo;

import com.facebookclone.fb_backend.dto.Album.SimpleAlbumDTO;

public class PhotoRequestDTO {
    private Long albumId;
    private String name;
    private String imageUrl;

    public PhotoRequestDTO(){}

    public PhotoRequestDTO(Long albumId, String name, String imageUrl) {
        this.albumId = albumId;
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public Long getAlbumId() {
        return albumId;
    }

    public void setAlbumId(Long albumId) {
        this.albumId = albumId;
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
}
