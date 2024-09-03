package com.app.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * Representa um usuário no sistema de e-commerce.
 */
@Entity
@Table(name = "TB_User")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User implements UserDetails {

    /**
     * O identificador único do usuário.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * O nome do usuário.
     */
    private String firstName;

    /**
     * O segondo nome do usuário.
     */
    private String lastName;

    /**
     * O email do usuário.
     */
    private String email;

    /**
     * A senha do usuário, usada para autenticação.
     */
    private String password;

    /**
     * A permissão do usuário no sistema.
     */
    @Enumerated(EnumType.STRING)
    private Role role;

    /**
     * O endereço do usuário.
     */
    @OneToOne
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority((role.name())));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
