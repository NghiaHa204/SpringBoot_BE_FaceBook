package com.facebookclone.fb_backend.service;

import com.facebookclone.fb_backend.entity.Album;
import com.facebookclone.fb_backend.entity.Photo;
import com.facebookclone.fb_backend.repository.AlbumRepository;
import com.facebookclone.fb_backend.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PhotoService {
    private final PhotoRepository photoRepository;
    private final AlbumRepository albumRepository;

    public PhotoService(PhotoRepository photoRepository, AlbumRepository albumRepository) {
        this.photoRepository = photoRepository;
        this.albumRepository = albumRepository;
    }

    public Photo createPhoto(Long albumId, String name, String imageUrl) {
        Album album = albumRepository.findById(albumId)
                .orElseThrow(() -> new RuntimeException("Không tồn tại Album có id: "+ albumId));

        Photo photo = new Photo();
        photo.setAlbum(album);
        photo.setCreatedAt(LocalDateTime.now());
        photo.setImageUrl(imageUrl);
        photo.setName(name);

        return photoRepository.save(photo);
    }

    public List<Photo> getPhotosByAlbumId(Long albumId) {
        return photoRepository.findByAlbumId(albumId);
    }

    public Photo getPhotoById(Long photoId){
        return photoRepository.findById(photoId).orElse(null);
    }

    public void deleteById(Long photoId){
        photoRepository.deleteById(photoId);
    }
}