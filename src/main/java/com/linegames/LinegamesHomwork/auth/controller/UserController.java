package com.linegames.LinegamesHomwork.auth.controller;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 정보 관련 웹 컨트롤러
 */
@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * 유저 정보 뷰로 이동
     *
     * @param model             {@link Model}
     * @param customUserDetails 현재 로그인한 유저의 정보를 담고 있는 {@link CustomUserDetails} 객체
     * @return 유저 정보를 보여주고, 수정할 수 있는 infoView 뷰 네임
     */
    @GetMapping("/info")
    public String userInfoGet(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("customUserDetails", customUserDetails);
        return "contents/user/infoView";
    }

    /**
     * 유저 정보 수정 로직
     *
     * @param model                     {@link Model}
     * @param authentication            현재 로그인한 유저의 정보를 담고 있는 {@link Authentication} 객체
     * @param modifiedCustomUserDetails 현재 로그인한 유저에 대해 변경된 정보를 담고 있는 {@link CustomUserDetails} 객체
     * @param userId                    변경할 유저에 대한 id값
     * @return 유저 정보 뷰 페이지 리다이렉션 URL
     */
    @PostMapping("/update/{userId}")
    public String updateUserPost(
            @ModelAttribute("customUserDetails") CustomUserDetails modifiedCustomUserDetails,
            @PathVariable("userId") Long userId, Authentication authentication, Model model) {

        CustomUserDetails customUserDetails = userDetailsService.findById(userId);
        customUserDetails.setUsername(modifiedCustomUserDetails.getUsername());
        userDetailsService.save(customUserDetails);

        // 업데이트에 성공하면 SecurityContextHolder에 있는 Authentication을 업데이트
        Authentication newAuthentication
                = new UsernamePasswordAuthenticationToken(customUserDetails, authentication.getCredentials(), customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        return "redirect:/user/info";
    }

    /**
     * 패스워드 찾기 뷰로 이동
     *
     * @return 패스워드 찾기 페이지 findPasswordView 뷰 네임
     */
    @GetMapping("/findPassword")
    public String findPasswordGet() {
        return "contents/auth/findPasswordView";
    }

}
