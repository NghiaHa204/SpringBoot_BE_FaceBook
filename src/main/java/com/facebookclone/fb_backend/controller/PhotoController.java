package com.facebookclone.fb_backend.controller;

import com.facebookclone.fb_backend.dto.Photo.PhotoRequestDTO;
import com.facebookclone.fb_backend.dto.Photo.PhotoResponseDTO;
import com.facebookclone.fb_backend.dto.Photo.SimplePhotoDTO;
import com.facebookclone.fb_backend.entity.Photo;
import com.facebookclone.fb_backend.service.PhotoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
    private final PhotoService photoService;

    public PhotoController(PhotoService photoService) {
        this.photoService = photoService;
    }

    @PostMapping
    public ResponseEntity<?> createPhoto(@RequestBody PhotoRequestDTO request) {
        try{
            Photo newPhoto = photoService.createPhoto(request.getAlbumId(), request.getName(), request.getImageUrl());
            PhotoResponseDTO responseDTO = new PhotoResponseDTO(
                    newPhoto.getId(),
                    newPhoto.getName(),
                    newPhoto.getImageUrl(),
                    newPhoto.getAlbum().getId(),
                    newPhoto.getCreatedAt()
            );
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                .body("Tạo photo that bai" + e.getMessage());
        }
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<SimplePhotoDTO>> getPhotosByAlbumId(@PathVariable Long albumId) {
        List<SimplePhotoDTO> simplePhotoDTOs = photoService.getPhotosByAlbumId(albumId);
        return ResponseEntity.ok(simplePhotoDTOs);
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<PhotoResponseDTO> getPhoto(@PathVariable Long photoId){
        return ResponseEntity.ok(photoService.getPhotoById(photoId));
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId){
        photoService.deleteById(photoId);
        return ResponseEntity.noContent().build();
    }


}