package com.example.vk.entities;

import jakarta.persistence.*;
import lombok.*;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AuthenticationLog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String principal;

    private boolean success;

    @OneToOne(mappedBy = "authenticationLog", cascade = CascadeType.ALL)
    private Audit audit;
}
