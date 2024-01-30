package com.brainstation23.erp.model.domain;


import com.brainstation23.erp.persistence.entity.AddressEntity;
import com.brainstation23.erp.persistence.entity.security.RoleEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class User {

    private Long id;
    private String username;
    private String email;
    private Set<RoleEntity> roles;
    private AddressEntity address;
}
