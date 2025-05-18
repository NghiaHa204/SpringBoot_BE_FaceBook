package com.facebookclone.fb_backend.service;

import com.facebookclone.fb_backend.dto.Like.LikeRequest;
import com.facebookclone.fb_backend.dto.Like.LikeResponse;
import com.facebookclone.fb_backend.entity.Like;
import com.facebookclone.fb_backend.entity.Post;
import com.facebookclone.fb_backend.entity.User;
import com.facebookclone.fb_backend.repository.LikeRepository;

import com.facebookclone.fb_backend.repository.PostRepository;
import com.facebookclone.fb_backend.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LikeService {
    private final LikeRepository likeRepository;

    private final UserRepository userRepository;

    private final PostRepository postRepository;

    public LikeService(LikeRepository likeRepository, UserRepository userRepository, PostRepository postRepository){
        this.likeRepository = likeRepository;
        this.userRepository = userRepository;
        this.postRepository = postRepository;
    }

    public LikeResponse addLike(LikeRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Post post = postRepository.findById(request.getPostId())
                .orElseThrow(() -> new RuntimeException("Post not found"));

        if (likeRepository.existsByUserIdAndPostId(request.getUserId(), request.getPostId())) {
            throw new RuntimeException("User already liked this post");
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        like.validate();
        likeRepository.save(like);
        return new LikeResponse("success", true);
    }

    public LikeResponse removeLike(LikeRequest request) {
        List<Like> likes = likeRepository.findByPostId(request.getPostId());
        Like like = likes.stream()
                .filter(l -> l.getUser().getId().equals(request.getUserId()) && l.getComment() == null)
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Like not found"));
        likeRepository.delete(like);
        return new LikeResponse("success", true);
    }

    public LikeResponse getLikesCount(Long postId) {
        long count = likeRepository.findByPostId(postId).stream()
                .filter(like -> like.getComment() == null)
                .count();
        return new LikeResponse("likesCount", count);
    }

    public LikeResponse getLikeStatus(Long postId, Long userId) {
        boolean isLiked = likeRepository.existsByUserIdAndPostId(userId, postId);
        return new LikeResponse("isLiked", isLiked);
    }
}