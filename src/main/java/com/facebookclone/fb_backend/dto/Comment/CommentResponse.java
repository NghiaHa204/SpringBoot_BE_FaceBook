package com.facebookclone.fb_backend.dto.Comment;

public class CommentResponse {
    private String key;
    private Object value;

    public CommentResponse(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    // Getters và setters
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
