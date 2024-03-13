package com.example.vk.controllers;

import com.example.vk.config.CustomUserDetails;
import com.example.vk.config.JwtService;
import com.example.vk.config.UserDetailsServiceImpl;
import com.example.vk.dtos.AuthRequestDTO;
import com.example.vk.dtos.JwtResponseDTO;
import com.example.vk.entities.Role;
import com.example.vk.entities.User;
import com.example.vk.entities.enums.ERole;
import com.example.vk.repositories.RoleRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.management.relation.RoleNotFoundException;
import java.util.HashSet;
import java.util.Set;

@Slf4j
@RestController
@RequestMapping("/api/users")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserController {
    final JwtService jwtService;
    final AuthenticationManager authenticationManager;
    final PasswordEncoder passwordEncoder;
    final UserDetailsServiceImpl userDetailsService;
    final RoleRepository roleRepository;


    @Autowired
    public UserController(JwtService jwtService,
                          AuthenticationManager authenticationManager,
                          UserDetailsServiceImpl userDetailsService,
                          PasswordEncoder passwordEncoder, RoleRepository roleRepository) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/signin")
    public ResponseEntity<?> createUser(@RequestBody AuthRequestDTO authRequestDTO) throws RoleNotFoundException {
        log.debug("password {}", authRequestDTO.getPassword());
        if (userDetailsService.isUserExist(authRequestDTO.getUsername())) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("User with this username already exists");
        }

        User user = User.builder().name(authRequestDTO.getUsername())
                .password(passwordEncoder.encode(authRequestDTO.getPassword()))
                .build();

        Set<Role> roles = new HashSet<>();
        Role userRole = roleRepository.findByName(ERole.ROLE_USERS.toString());
        if (userRole == null) {
            throw new RoleNotFoundException("Role not found");
        }

        roles.add(userRole);
        user.setRoles(roles);

        userDetailsService.createUser(user);

        return ResponseEntity.ok("User was created");
    }

    @PostMapping("/login")
    public JwtResponseDTO authenticateAndGetToken(@RequestBody AuthRequestDTO authRequestDTO) {
        CustomUserDetails userDetails = userDetailsService
                .loadUserByUsername(authRequestDTO.getUsername());

        log.info("username {}", userDetails.getUsername());
        if (passwordEncoder.matches(authRequestDTO.getPassword(),
                userDetails.getPassword())) {
            return JwtResponseDTO.builder()
                    .accessToken(jwtService.generateToken(authRequestDTO
                            .getUsername())).build();
        } else {
            throw new BadCredentialsException("Invalid username or password");
        }
    }

    @GetMapping("/logout")
    public ResponseEntity<?> logout(HttpServletRequest request) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null) {
            request.getSession().invalidate();
        }

        return ResponseEntity.ok("Logout");
    }
}
