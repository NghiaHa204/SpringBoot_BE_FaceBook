package com.facebookclone.fb_backend.controller;

import com.facebookclone.fb_backend.dto.Like.LikeRequest;
import com.facebookclone.fb_backend.dto.Like.LikeResponse;
import com.facebookclone.fb_backend.entity.Like;
import com.facebookclone.fb_backend.repository.LikeRepository;
import com.facebookclone.fb_backend.service.LikeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/likes")
public class LikeController {
    private final LikeService likeService;
    private final LikeRepository likeRepository;

    public LikeController(LikeService likeService, LikeRepository likeRepository){
        this.likeService = likeService;
        this.likeRepository = likeRepository;
    }

    @PostMapping
    public ResponseEntity<LikeResponse> addLike(@RequestBody LikeRequest request) {
        return ResponseEntity.ok(likeService.addLike(request));
    }

    @DeleteMapping
    public ResponseEntity<LikeResponse> removeLike(@RequestBody LikeRequest request) {
        return ResponseEntity.ok(likeService.removeLike(request));
    }

    @GetMapping("/post/{postId}/count")
    public ResponseEntity<LikeResponse> getLikesCount(@PathVariable Long postId) {
        return ResponseEntity.ok(likeService.getLikesCount(postId));
    }

    @GetMapping("/post/{postId}/status")
    public ResponseEntity<LikeResponse> getLikeStatus(@PathVariable Long postId, @RequestParam Long userId) {
        return ResponseEntity.ok(likeService.getLikeStatus(postId, userId));
    }

//    @GetMapping("/posts")
//    public ResponseEntity<Map<Long, Map<String, Object>>> getLikesForPosts(@RequestParam List<Long> postIds, @RequestParam Long userId) {
//        Map<Long, Map<String, Object>> result = new HashMap<>();
//        for (Long postId : postIds) {
//            long count = likeRepository.findByPostId(postId).stream()
//                    .filter(like -> like.getComment() == null)
//                    .count();
//            boolean isLiked = likeRepository.existsByUserIdAndPostId(userId, postId);
//            result.put(postId, Map.of("likesCount", count, "isLiked", isLiked));
//        }
//        return ResponseEntity.ok(result);
//    }
}