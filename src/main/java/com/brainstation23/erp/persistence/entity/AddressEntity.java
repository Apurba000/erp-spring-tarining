package com.brainstation23.erp.persistence.entity;


import com.brainstation23.erp.persistence.entity.security.UserEntity;

import javax.persistence.*;

import static com.brainstation23.erp.constant.EntityConstant.ADDRESS;

@Entity
@Table(name = ADDRESS)
public class AddressEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(mappedBy = ADDRESS)
    private UserEntity user;
}
