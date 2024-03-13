package com.example.vk.config;

import com.example.vk.entities.Audit;
import com.example.vk.entities.AuthenticationLog;
import com.example.vk.services.AuthenticationLogService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
public class AuthenticationEvents {

    private final AuthenticationLogService authenticationLogService;

    public AuthenticationEvents(AuthenticationLogService authenticationLogService) {
        this.authenticationLogService = authenticationLogService;
    }

    @EventListener
    public void handleAuthenticationSuccessEvent(AuthenticationSuccessEvent event) {
        Authentication authentication = event.getAuthentication();
        authenticationLogService.saveSuccessLog(authentication.getPrincipal());
    }

    @EventListener
    public void handleAuthenticationFailureEvent(AbstractAuthenticationFailureEvent event) {
        Authentication authentication = event.getAuthentication();
        authenticationLogService.saveFailtureLog((String) authentication.getPrincipal());
    }

}
