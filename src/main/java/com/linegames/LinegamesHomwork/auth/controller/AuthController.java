package com.linegames.LinegamesHomwork.auth.controller;

import com.linegames.LinegamesHomwork.auth.config.CustomAuthenticationFailureHandler;
import com.linegames.LinegamesHomwork.auth.config.SecurityConfig;
import com.linegames.LinegamesHomwork.auth.model.AuthorityEnum;
import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;

/**
 * 인증 관련 웹 컨트롤러
 */
@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 로그인 뷰로 이동.
     * <p>
     * {@link GetMapping}을 쓰지 않는 이유 :
     * 로그인 페이지 리다이렉션은 GET /auth/login, 로그인 처리 로직의 경우 POST /auth/login 으로 요청하고있다.
     * 여기서 로그인 처리 시 (POST /auth/login에 요청) 실패한 경우, {@link CustomAuthenticationFailureHandler}에서 인증실패 후처리 작업을 수행한다.
     * 이때 onAuthenticationFailure({@link HttpServletRequest} request, {@link HttpServletResponse} response)의 request객체의 method는 그대로 Post다.
     * {@link GetMapping}으로 GET /auth/login 을 컨트롤러에 매핑하면,
     * POST /auth/login는 {@link PostMapping}을 따로 정의하지 않는 이상 찾을 수 없게 되고 405 Post method now allowed 에러가 발생한다.
     * 따라서 {@link RequestMapping}을 선언하여 매핑하고 {@link HttpMethod}를 명시하지 않으면 둘 다 인식된다.
     * 이때 POST /auth/login은 {@link SecurityConfig}에서 로그인 프로세스 URL로 설정했기 때문에 Spring Security에서 처리하게 되고, 정상적으로 동작한다.
     *
     * @return 로그인 페이지인 loginView 뷰 네임
     * @see <a href="https://zgundam.tistory.com/53?category=430446">login validation 후처리</a>
     * @see <a href="https://zgundam.tistory.com/guestbook">login validation 후처리 시 405 발생</a>
     */
    @RequestMapping("/login")
    public String login() {
        return "contents/auth/loginView";
    }

    /**
     * 회원가입 뷰로 이동
     *
     * @param model {@link Model}
     * @return 회원가입 페이지인 signupView 뷰 네임
     */
    @GetMapping("/signup")
    public String signUpGet(Model model) {
        model.addAttribute("customUserDetails", new CustomUserDetails());
        return "contents/auth/signupView";
    }

    /**
     * 회원가입 수행 로직
     *
     * @param customUserDetails 회원가입할 유저의 정보를 담은 {@link CustomUserDetails} 객체
     * @param bindingResult     회원가입 Validation 결과를 담은 {@link BindingResult} 객체
     * @return 로그인 페이지 URL 리다이렉션
     */
    @PostMapping("/signup")
    public String signUpPost(@ModelAttribute @Valid CustomUserDetails customUserDetails, BindingResult bindingResult) {
        /** Data Validation **/
        // username이 이미 존재하는 경우 에러
        CustomUserDetails existCustomUserDetails = userDetailsService.loadUserByUsername(customUserDetails.getUsername());

        if (existCustomUserDetails != null) {
            bindingResult.addError(
                    new FieldError("customUserDetails", "username", "Username Duplicated")
            );
        }

        // Data Validation 실패 시 에러
        if (bindingResult.hasErrors()) {
            return "contents/auth/signupView";
        }
        /** Data Validation **/

        // 패스워드 암호화
        customUserDetails.setPassword(
                passwordEncoder
                        .encode(customUserDetails.getPassword())
        );

        // 권한 설정
        List<String> authorities = new ArrayList<>();
        authorities.add(AuthorityEnum.MEMBER.getAuthorityName());
        //authorities.add(RoleEnum.ADMIN.getRoleName());
        customUserDetails.setAuthorities(authorities);

        // Activate
        customUserDetails.setAccountNonExpired(true);
        customUserDetails.setAccountNonLocked(true);
        customUserDetails.setEnabled(true);
        customUserDetails.setCredentialsNonExpired(true);

        userDetailsService.save(customUserDetails);

        return "redirect:/auth/login";
    }

}
