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
import org.springframework.session.FindByIndexNameSessionRepository;
import org.springframework.session.MapSessionRepository;
import org.springframework.session.SessionRepository;
import org.springframework.session.security.SpringSessionBackedSessionRegistry;
import org.springframework.session.Session;

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

    @Autowired
    private FindByIndexNameSessionRepository sessionRepository;

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

        // 중복 로그인 방지
        http
                .sessionManagement()
                    .maximumSessions(1)
                    .sessionRegistry(sessionRegistry()) // 중복 로그인 방지를 위해 반드시 sessionRegistry를 등록해야 함
                    .maxSessionsPreventsLogin(true);
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

    @Bean
    public SpringSessionBackedSessionRegistry<Session> sessionRegistry() {
        return new SpringSessionBackedSessionRegistry<>(this.sessionRepository);
    }
}
