package com.brainstation23.erp.controller.rest;


import com.brainstation23.erp.model.dto.JwtResponse;
import com.brainstation23.erp.model.dto.LoginRequest;
import com.brainstation23.erp.model.dto.MessageResponse;
import com.brainstation23.erp.model.dto.SignUpRequest;
import com.brainstation23.erp.persistence.entity.security.ERole;
import com.brainstation23.erp.persistence.entity.security.RoleEntity;
import com.brainstation23.erp.persistence.entity.security.UserEntity;
import com.brainstation23.erp.persistence.repository.RoleRepository;
import com.brainstation23.erp.persistence.repository.UserRepository;
import com.brainstation23.erp.security.JwtUtils;
import com.brainstation23.erp.security.service.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/auth")
@RequiredArgsConstructor
public class AuthController {
    private static final String EXIST_USERNAME = "Error: Username is already taken!";
    private static final String EXIST_EMAIL = "Error: Email is already in use!";
    private static final String NOT_EXIST_ROLE = "Error: Role is not found.";
    private static final String REGISTRATION_SUCCESSFUL = "User registered successfully!";

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder encoder;
    private final JwtUtils jwtUtils;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles));
    }



    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if (userRepository.existsByUsername(signUpRequest.getUsername()))
            return ResponseEntity.badRequest().body(new MessageResponse(EXIST_USERNAME));

        if (userRepository.existsByEmail(signUpRequest.getEmail()))
            return ResponseEntity.badRequest().body(new MessageResponse(EXIST_EMAIL));

        // Create new user's account
        userRepository.save(createNewUser(signUpRequest));
        return ResponseEntity.ok(new MessageResponse(REGISTRATION_SUCCESSFUL));
    }

    private UserEntity createNewUser(SignUpRequest signUpRequest){
        UserEntity user = new UserEntity(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        Set<String> strRoles = signUpRequest.getRole();
        Set<RoleEntity> roles = new HashSet<>();

        if (strRoles == null) {
            RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                    .orElseThrow(() -> new RuntimeException(NOT_EXIST_ROLE));
            roles.add(userRole);
        } else {
            strRoles.forEach(role -> {
                if (ERole.ROLE_ADMIN.name().equals(role)) {
                    RoleEntity adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                            .orElseThrow(() -> new RuntimeException(NOT_EXIST_ROLE));
                    roles.add(adminRole);
                } else {
                    RoleEntity userRole = roleRepository.findByName(ERole.ROLE_USER)
                            .orElseThrow(() -> new RuntimeException(NOT_EXIST_ROLE));
                    roles.add(userRole);
                }
            });
        }
        return user.setRoles(roles);
    }
}
