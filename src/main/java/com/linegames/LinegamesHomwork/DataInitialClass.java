package com.linegames.LinegamesHomwork;

import com.linegames.LinegamesHomwork.auth.model.AuthorityEnum;
import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;

/**
 * 데이터 초기화 클래스
 */
@Configuration
public class DataInitialClass {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ADMIN 계정 추가
     */
    @PostConstruct
    public void addAdminAccount() {
        CustomUserDetails adminUserDetails = new CustomUserDetails();
        adminUserDetails.setAccountNonExpired(true);
        adminUserDetails.setAccountNonLocked(true);
        adminUserDetails.setCredentialsNonExpired(true);
        adminUserDetails.setEnabled(true);

        adminUserDetails.setUsername("admin");
        adminUserDetails.setPassword(passwordEncoder.encode("1234"));

        List<String> authorities = new ArrayList<>();
        authorities.add(AuthorityEnum.ADMIN.getAuthorityName());
        adminUserDetails.setAuthorities(authorities);

        ((CustomUserDetailsService) userDetailsService).save(adminUserDetails);
    }

}
