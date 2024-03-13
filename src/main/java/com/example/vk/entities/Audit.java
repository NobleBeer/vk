package com.example.vk.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.HashMap;

@Builder
@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Audit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @CreationTimestamp
    private LocalDateTime createAt;
    private boolean isAccessAllowed;

    @ElementCollection
    @MapKeyColumn(name = "param_name")
    @Column(name = "param_value")
    @CollectionTable(name = "audit_params", joinColumns = @JoinColumn(name = "audit_id"))
    private Map<String, String> params = new HashMap<>();

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "authentication_log_id")
    private AuthenticationLog authenticationLog;

}
