package com.linegames.LinegamesHomwork.auth.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 유저 정보 클래스
 */
@Data
@NoArgsConstructor
@Entity
public class CustomUserDetails implements UserDetails, Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Column(unique = true)
    @NonNull
    private String username; // 닉네임
    private String password; // 패스워드

    private boolean accountNonExpired;
    private boolean accountNonLocked;
    private boolean credentialsNonExpired;
    private boolean enabled;

    @ElementCollection(fetch = FetchType.EAGER)
    private List<String> authorities = new ArrayList<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        for (String authority : this.authorities) {
            GrantedAuthority grantedAuthority = (GrantedAuthority) () -> authority;
            authorities.add(grantedAuthority);
        }

        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
