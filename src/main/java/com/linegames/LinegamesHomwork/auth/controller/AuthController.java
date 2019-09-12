package com.linegames.LinegamesHomwork.auth.controller;

import com.linegames.LinegamesHomwork.auth.model.AuthorityEnum;
import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
     * 로그인 뷰로 이동
     *
     * @return 로그인 페이지인 loginView 뷰 네임
     */
    @GetMapping("/login")
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
