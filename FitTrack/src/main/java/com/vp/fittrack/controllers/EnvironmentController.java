package com.vp.fittrack.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EnvironmentController {

  private final String apiKey;

  @Autowired
  public EnvironmentController(@Value("${API_KEY}") String apiKey) {
    this.apiKey = apiKey;
  }

  @GetMapping("/api-key")
  public ResponseEntity<String> getApiKey() {
    return ResponseEntity.ok(apiKey);
  }
}
