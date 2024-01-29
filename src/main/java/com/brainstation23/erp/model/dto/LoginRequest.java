package com.brainstation23.erp.model.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;

@ToString
@Getter
@Setter
public class LoginRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;
}
