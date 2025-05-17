package com.facebookclone.fb_backend.controller;

import com.facebookclone.fb_backend.dto.Album.AlbumRequestCreateDTO;
import com.facebookclone.fb_backend.dto.Album.AlbumResponseDTO;
import com.facebookclone.fb_backend.dto.Album.SimpleAlbumDTO;
import com.facebookclone.fb_backend.entity.Album;
import com.facebookclone.fb_backend.service.AlbumService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/albums")
public class AlbumController {
    private final AlbumService albumService;

    public AlbumController(AlbumService albumService) {
        this.albumService = albumService;
    }

    @PostMapping
    public ResponseEntity<?> createAlbum(@RequestBody AlbumRequestCreateDTO request) {
        try {
            Album newAlbum = albumService.createAlbum(request.getOwnerId(), request.getName()); // Trả về Entity
            AlbumResponseDTO responseDTO = albumService.toDTO(newAlbum); // Trả về DTO
            return ResponseEntity.status(HttpStatus.CREATED).body(responseDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                                 .body("Tạo album thất bại: "+ e.getMessage());
        }
    }

    @GetMapping("/owner/{ownerId}")
    public ResponseEntity<List<SimpleAlbumDTO>> getAlbumsByOwnerId(@PathVariable Long ownerId) {
        List<SimpleAlbumDTO> simpleAlbums = albumService.getAlbumsByOwnerId(ownerId);
        return ResponseEntity.ok(simpleAlbums);
    }

    @GetMapping("/{albumId}")
    public ResponseEntity<SimpleAlbumDTO> getAlbumById(@PathVariable Long albumId){
        return ResponseEntity.ok(albumService.getAlbumById(albumId));
    }

    @DeleteMapping("/{albumId}")
    public ResponseEntity<Void> deleteAlbumById(@PathVariable Long albumId){
        albumService.deleteAlbumById(albumId);
        return ResponseEntity.noContent().build();
    }
}