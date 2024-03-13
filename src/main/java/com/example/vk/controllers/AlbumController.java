package com.example.vk.controllers;

import com.example.vk.config.UserDetailsServiceImpl;
import com.example.vk.entities.User;
import com.example.vk.services.AuditService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@Slf4j
@RestController
@RequestMapping("/api/albums")
@CrossOrigin(origins = "*", maxAge = 3600)
public class AlbumController {
    @Value("${base.url}")
    private String url;

    private final AuditService auditService;
    private final UserDetailsServiceImpl userDetailsService;
    private ApplicationEventPublisher applicationEventPublisher;

    public AlbumController(AuditService auditService,
                           UserDetailsServiceImpl userDetailsService) {
        this.auditService = auditService;
        this.userDetailsService = userDetailsService;
    }

    @GetMapping("/")
    public ResponseEntity<?> getPosts() {

        User user = userDetailsService.getUser();
        String uri = url + "albums";
        RestTemplate restTemplate = new RestTemplate();
        var template = restTemplate.getForObject(uri, Object.class);
        return ResponseEntity.ok(template);
    }
}
