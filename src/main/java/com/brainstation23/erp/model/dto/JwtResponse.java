package com.brainstation23.erp.model.dto;

import lombok.*;

import java.util.List;


@ToString
@Getter
@Setter
@RequiredArgsConstructor
public class JwtResponse {

    private final String token;
    private String type = "Bearer";
    private final Long id;
    private final String username;
    private final String email;
    private final List<String> roles;

}
