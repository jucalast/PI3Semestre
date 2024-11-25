
package com.app.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@Entity
@Table(name = "TB_USER_ADDRESS")
public class UserAddress {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private UserModel user;

    @ManyToOne
    @JoinColumn(name = "ADDRESS_ID")
    private AddressModel address;

    @Column(name = "ADDRESS_TYPE")
    private String addressType;
}