package com.facebookclone.fb_backend.dto;

public class Response {
    private String key;
    private Object value;

    public Response(String key, Object value) {
        this.key = key;
        this.value = value;
    }

    // Getters, setters
    public String getKey() { return key; }
    public void setKey(String key) { this.key = key; }
    public Object getValue() { return value; }
    public void setValue(Object value) { this.value = value; }
}
