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

@Controller
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 로그인 뷰
     */
    @GetMapping("/login")
    public String login() {
        return "contents/auth/loginView";
    }

    /**
     * 회원가입 뷰
     */
    @GetMapping("/signup")
    public String signUpGet(Model model) {
        model.addAttribute("customUserDetails", new CustomUserDetails());
        return "contents/auth/signupView";
    }

    /**
     * 회원가입 로직
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
