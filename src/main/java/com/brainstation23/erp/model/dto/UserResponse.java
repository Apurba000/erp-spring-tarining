package com.brainstation23.erp.model.dto;

import com.brainstation23.erp.persistence.entity.AddressEntity;
import com.brainstation23.erp.persistence.entity.security.RoleEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Set;

@ToString
@Getter
@Setter
public class UserResponse {
    private Long id;
    private String username;
    private String email;
    private Set<RoleEntity> roles;
    private AddressEntity address;

}
