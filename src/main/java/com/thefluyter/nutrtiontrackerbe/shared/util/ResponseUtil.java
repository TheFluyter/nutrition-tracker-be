package com.thefluyter.nutrtiontrackerbe.shared.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    
    public static <T> ResponseEntity<T> ok(T body) {
        return ResponseEntity.ok(body);
    }
    
    public static <T> ResponseEntity<T> created(T body) {
        return ResponseEntity.status(HttpStatus.CREATED).body(body);
    }
    
    public static <T> ResponseEntity<T> notFound() {
        return ResponseEntity.notFound().build();
    }
    
    @SuppressWarnings("unchecked")
    public static <T> ResponseEntity<T> badRequest(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return ResponseEntity.badRequest().body((T) error);
    }
    
    @SuppressWarnings("unchecked")
    public static <T> ResponseEntity<T> internalServerError(String message) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body((T) error);
    }
}
