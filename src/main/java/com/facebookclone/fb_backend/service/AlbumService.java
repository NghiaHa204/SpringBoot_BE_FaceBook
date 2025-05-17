package com.facebookclone.fb_backend.service;

import com.facebookclone.fb_backend.dto.Album.AlbumRequestCreateDTO;
import com.facebookclone.fb_backend.dto.Album.AlbumResponseDTO;
import com.facebookclone.fb_backend.dto.Album.SimpleAlbumDTO;
import com.facebookclone.fb_backend.dto.Photo.SimplePhotoDTO;
import com.facebookclone.fb_backend.dto.User.SimpleUserDTO;
import com.facebookclone.fb_backend.entity.Album;
import com.facebookclone.fb_backend.entity.User;
import com.facebookclone.fb_backend.repository.AlbumRepository;
import com.facebookclone.fb_backend.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AlbumService {
    private final AlbumRepository albumRepository;
    private final UserRepository userRepository;

    public AlbumService(AlbumRepository albumRepository, UserRepository userRepository) {
        this.albumRepository = albumRepository;
        this.userRepository = userRepository;
    }

    public Album createAlbum(Long ownerId, String name) {
        User owner = userRepository.findById(ownerId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy user"));

        Album album = new Album();
        album.setName(name);
        album.setOwner(owner);
        album.setCreatedAt(LocalDateTime.now());

        return albumRepository.save(album);
    }

    // Lấy toàn bộ Album của một User
    public List<SimpleAlbumDTO> getAlbumsByOwnerId(Long ownerId) {
        List<Album> albums = albumRepository.findByOwnerId(ownerId);
        return albums
                .stream()
                .map(album -> new SimpleAlbumDTO(
                    album.getId(),
                    album.getName(),
                    album.getCreatedAt(),
                    album.getPhotos()
                            .stream()
                            .map(photo -> new SimplePhotoDTO(
                                photo.getId(),
                                photo.getName(),
                                photo.getImageUrl(),
                                photo.getCreatedAt()
                    )).collect(Collectors.toList())
        )).collect(Collectors.toList());
    }

    public SimpleAlbumDTO getAlbumById(Long albumId){
        Album album = albumRepository.findById(albumId)
                .orElseThrow(()-> new RuntimeException("Không tồn tại Album có id: "+albumId));
        return new SimpleAlbumDTO(
                album.getId(),
                album.getName(),
                album.getCreatedAt(),
                album.getPhotos()
                        .stream()
                        .map(photo -> new SimplePhotoDTO(
                                photo.getId(),
                                photo.getName(),
                                photo.getImageUrl(),
                                photo.getCreatedAt()
                        )).collect(Collectors.toList())
        );
    }

    public void deleteAlbumById(Long albumId){
        albumRepository.deleteById(albumId);
    }

    // Response DTO
    public AlbumResponseDTO toDTO (Album album){
        SimpleUserDTO userDTO = new SimpleUserDTO(album.getOwner().getId()
                                                , album.getOwner().getName()
                                                , album.getOwner().getEmail());
        return new AlbumResponseDTO(album.getId(), album.getName(),album.getCreatedAt(), userDTO);
    }
}