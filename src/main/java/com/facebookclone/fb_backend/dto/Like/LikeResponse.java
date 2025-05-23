package com.facebookclone.fb_backend.dto.Like;

public class LikeResponse {
    private String key;
    private Object value;

    public LikeResponse() {}

    public LikeResponse(String key, Object value) {
        this.key = key;
        this.value = value;
    }

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
