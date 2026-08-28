package com.civicconnect.service;

import com.civicconnect.dto.LoginRequest;
import com.civicconnect.dto.RegisterRequest;
import com.civicconnect.dto.AuthResponse;
import com.civicconnect.dto.UserDTO;
import com.civicconnect.exception.ResourceNotFoundException;
import com.civicconnect.model.User;
import com.civicconnect.repository.UserRepository;
import com.civicconnect.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    public AuthResponse register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already registered");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPhone(request.getPhone());
        user.setCity(request.getCity());
        user.setState(request.getState());
        user.setAddress(request.getAddress());
        user.setRole("CITIZEN");
        user.setStatus("ACTIVE");

        User savedUser = userRepository.save(user);
        String token = jwtTokenProvider.generateToken(savedUser.getEmail());

        return new AuthResponse(
            token,
            "User registered successfully",
            convertToDTO(savedUser)
        );
    }

    public AuthResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Invalid password");
        }

        String token = jwtTokenProvider.generateToken(user.getEmail());

        return new AuthResponse(
            token,
            "Login successful",
            convertToDTO(user)
        );
    }

    public UserDTO getUserByEmail(String email) {
        User user = userRepository.findByEmail(email)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return convertToDTO(user);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
            user.getId(),
            user.getEmail(),
            user.getFirstName(),
            user.getLastName(),
            user.getPhone(),
            user.getRole(),
            user.getCity(),
            user.getState()
        );
    }
}
