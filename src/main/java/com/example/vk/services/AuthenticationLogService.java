package com.example.vk.services;

import com.example.vk.entities.AuthenticationLog;
import com.example.vk.repositories.AuthenticationLogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationLogService {

    private final AuthenticationLogRepository authenticationLogRepository;

    @Autowired
    public AuthenticationLogService(AuthenticationLogRepository authenticationLogRepository) {
        this.authenticationLogRepository = authenticationLogRepository;
    }

    public void saveSuccessLog(Object principal) {
        AuthenticationLog authenticationLog = new AuthenticationLog();
        authenticationLog.setPrincipal(principal.toString());
        authenticationLog.setSuccess(true);

        authenticationLogRepository.save(authenticationLog);
    }

    public void saveFailtureLog(String userName) {
        AuthenticationLog authenticationLog = new AuthenticationLog();
        authenticationLog.setPrincipal(userName);
        authenticationLog.setSuccess(false);

        authenticationLogRepository.save(authenticationLog);
    }
}
