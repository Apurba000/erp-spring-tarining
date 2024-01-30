package com.brainstation23.erp.persistence.entity;


import com.brainstation23.erp.persistence.entity.security.UserEntity;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.persistence.*;

import static com.brainstation23.erp.constant.EntityConstant.ADDRESS;
import static com.brainstation23.erp.constant.EntityConstant.USER_ID;

@Entity
@Table(name = ADDRESS)
@Getter
@Setter
@Accessors(chain = true)
public class AddressEntity {

    @Id
    @Column(name = USER_ID)
    private Long id;

    private String street;
    private String city;

    @OneToOne
    @MapsId
    @JoinColumn(name = USER_ID)
    private UserEntity user;
}
