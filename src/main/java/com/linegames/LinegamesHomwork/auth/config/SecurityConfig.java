package com.linegames.LinegamesHomwork.auth.config;

import com.linegames.LinegamesHomwork.auth.model.AuthorityEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * 보안 관련 설정
 */
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    /**
     * {@link com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService}
     */
    @Autowired
    private UserDetailsService userDetailsService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                // CSRF 토큰 활용 비활성화
                .csrf()
                    .disable()

                .authorizeRequests()
                    // ADMIN 만 접근 가능
                    .antMatchers("/admin/**")
                        .hasAnyAuthority(AuthorityEnum.ADMIN.getAuthorityName())

                    // ADMIN, MEMBER 중 하나의 Authority을 가지고 있으면 접근 가능
                    .antMatchers("/user/info")
                        .hasAnyAuthority(
                            AuthorityEnum.ADMIN.getAuthorityName()
                            , AuthorityEnum.MEMBER.getAuthorityName()
                    )

                    // 나머지는 모두 접근 가능
                    .antMatchers("/**")
                        .permitAll()

                    .anyRequest()
                        .authenticated();

        // Form 로그인 사용
        http
                .formLogin()
                    .loginPage("/auth/login")
                    .permitAll()
                    .and()

                .logout()
                    .logoutUrl("/auth/logout")
                    .logoutSuccessUrl("/")
                    .permitAll();

        http
                .userDetailsService(userDetailsService);

        // h2 콘솔 X-Frame-Options in Spring Security 중지
        http
                .headers()
                    .frameOptions()
                    .disable();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth
                .userDetailsService(userDetailsService)
                .passwordEncoder(passwordEncoder());
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

}
