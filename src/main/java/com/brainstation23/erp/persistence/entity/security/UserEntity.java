package com.brainstation23.erp.persistence.entity.security;

import com.brainstation23.erp.persistence.entity.AddressEntity;
import lombok.*;
import lombok.experimental.Accessors;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;

import static com.brainstation23.erp.constant.EntityConstant.*;

@Entity
@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
@RequiredArgsConstructor
@Table(name = USER,
        uniqueConstraints = {
                @UniqueConstraint(columnNames = USER_COLUMN_USERNAME),
                @UniqueConstraint(columnNames = USER_COLUMN_EMAIL)
        })
public class UserEntity {
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private Long id;

        @NonNull
        @NotBlank
        @Size(max = 20)
        private String username;

        @NonNull
        @NotBlank
        @Size(max = 50)
        @Email
        private String email;

        @NonNull
        @NotBlank
        @Size(max = 120)
        private String password;

        @ManyToMany(fetch = FetchType.LAZY)
        @JoinTable(  name = USER_ROLE,
                joinColumns = @JoinColumn(name = USER_ROLE_USER_ID),
                inverseJoinColumns = @JoinColumn(name = USER_ROLE_ROLE_ID))
        private Set<RoleEntity> roles = new HashSet<>();



        /*      Modeling with FOREIGN Key

        ------users------                               -----address----
        id INT                                          id INT
        username VARCHAR                                street VARCHAR
        address_id INT                                  city VARCHAR

         */

        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = ADDRESS_ID, referencedColumnName = ID)
        private AddressEntity address;

}
