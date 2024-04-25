package com.clearsolutions.task.solution.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseUtil {
    public static ResponseEntity<?> generateResponse(String message, HttpStatus status, Object... o) {
        return getResponse(message, status, o);
    }

    public static ResponseEntity<?> generateError(String message, HttpStatus status, Object... o) {
        return getResponse(message, status, o);
    }

    private static ResponseEntity<?> getResponse(String message, HttpStatus status, Object... o) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        if (o.length > 0 && o[0] != null) {
            map.put("data", o[0]);
        }
        return new ResponseEntity<>(map, status);
    }
}