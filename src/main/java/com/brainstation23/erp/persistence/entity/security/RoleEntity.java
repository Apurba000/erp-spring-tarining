package com.brainstation23.erp.persistence.entity.security;


import com.brainstation23.erp.constant.EntityConstant;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;


@Entity(name = EntityConstant.ROLE)
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
public class RoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Enumerated(EnumType.STRING)
    @Column(length = 20)
    @NonNull
    private ERole name;

}