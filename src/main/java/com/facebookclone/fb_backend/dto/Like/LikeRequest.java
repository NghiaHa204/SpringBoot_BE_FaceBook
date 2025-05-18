package com.facebookclone.fb_backend.dto.Like;

public class LikeRequest {
    private Long userId;
    private Long postId;

    public LikeRequest() {}

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}
