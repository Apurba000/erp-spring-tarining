package com.brainstation23.erp.controller.rest;

import com.brainstation23.erp.mapper.UserMapper;
import com.brainstation23.erp.model.dto.UserResponse;
import com.brainstation23.erp.security.service.UserDetailsServiceImpl;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springdoc.api.annotations.ParameterObject;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "api/users")
public class UserController {
    private final UserDetailsServiceImpl userDetailsService;
    private final UserMapper userMapper;

    @Operation(summary = "Get All Users")
    @GetMapping("/all")
    public ResponseEntity<Page<UserResponse>> getAll(@ParameterObject Pageable pageable) {
        log.info("Getting List of Users");
        var domains = userDetailsService.getAll(pageable);
        return ResponseEntity.ok(domains.map(userMapper::domainToResponse));
    }

}
