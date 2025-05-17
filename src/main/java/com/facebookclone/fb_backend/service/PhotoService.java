package com.facebookclone.fb_backend.service;

import com.facebookclone.fb_backend.dto.Album.SimpleAlbumDTO;
import com.facebookclone.fb_backend.dto.Photo.PhotoResponseDTO;
import com.facebookclone.fb_backend.dto.Photo.SimplePhotoDTO;
import com.facebookclone.fb_backend.entity.Album;
import com.facebookclone.fb_backend.entity.Photo;
import com.facebookclone.fb_backend.repository.AlbumRepository;
import com.facebookclone.fb_backend.repository.PhotoRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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

    public List<SimplePhotoDTO> getPhotosByAlbumId(Long albumId) {
        List<Photo> photos = photoRepository.findByAlbumId(albumId);
        return photos
                .stream()
                .map(photo -> new SimplePhotoDTO(
                        photo.getId(),
                        photo.getName(),
                        photo.getImageUrl(),
                        photo.getCreatedAt()
                ))
                .collect(Collectors.toList());
    }

    public PhotoResponseDTO getPhotoById(Long photoId){
        Photo photo = photoRepository.findById(photoId)
                .orElseThrow(() -> new RuntimeException("Không tồn tại photo co id: "+photoId));
        return new PhotoResponseDTO(
                photo.getId(),
                photo.getName(),
                photo.getImageUrl(),
                photo.getAlbum().getId(),
                photo.getCreatedAt()
                );
    }

    public void deleteById(Long photoId){
        photoRepository.deleteById(photoId);
    }

}