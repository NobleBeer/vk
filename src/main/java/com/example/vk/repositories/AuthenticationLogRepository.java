package com.example.vk.repositories;

import com.example.vk.entities.AuthenticationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthenticationLogRepository extends JpaRepository<AuthenticationLog, Long> {
}
