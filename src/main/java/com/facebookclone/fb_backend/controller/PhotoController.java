package com.facebookclone.fb_backend.controller;

import com.facebookclone.fb_backend.entity.Photo;
import com.facebookclone.fb_backend.service.PhotoService;
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
    public ResponseEntity<Photo> createPhoto(@RequestBody Long albumId, String name, String imageUrl) {
        return ResponseEntity.ok(photoService.createPhoto(albumId, name, imageUrl));
    }

    @GetMapping("/album/{albumId}")
    public ResponseEntity<List<Photo>> getPhotosByAlbumId(@PathVariable Long albumId) {
        return ResponseEntity.ok(photoService.getPhotosByAlbumId(albumId));
    }

    @GetMapping("/{photoId}")
    public ResponseEntity<Photo> getPhoto(@PathVariable Long photoId){
        Photo photo = photoService.getPhotoById(photoId);
        if(photo == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(photo);
    }

    @DeleteMapping("/{photoId}")
    public ResponseEntity<Void> deletePhoto(@PathVariable Long photoId){
        photoService.deleteById(photoId);
        return ResponseEntity.noContent().build();
    }
}