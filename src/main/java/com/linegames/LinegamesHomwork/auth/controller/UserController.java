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

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * 유저 정보 뷰
     */
    @GetMapping("/info")
    public String userInfoGet(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("customUserDetails", customUserDetails);
        return "contents/user/infoView";
    }

    /**
     * 유저 정보 수정 로직
     */
    @PostMapping("/update/{userId}")
    public String updateUserPost(
            @ModelAttribute("customUserDetails") CustomUserDetails modifiedCustomUserDetails,
            @PathVariable("userId") Long userId, Authentication authentication, Model model) {

        CustomUserDetails customUserDetails = userDetailsService.findById(userId);
        customUserDetails.setUsername(modifiedCustomUserDetails.getUsername());
        userDetailsService.save(customUserDetails);

        // 업데이트에 성공하면 SecurityContextHolder에 있는 Authentication을 업데이트
        // TODO : 리퀘스트가 올 때마다 userService에서 정보를 받아서 갱신하도록 변경
        //        현재는 정보가 바뀐 후 다른 브라우저에서는 갱신되지 않고, 이 로직을 수행한 서버에서만 반영됨
        Authentication newAuthentication
                = new UsernamePasswordAuthenticationToken(customUserDetails, authentication.getCredentials(), customUserDetails.getAuthorities());
        SecurityContextHolder.getContext().setAuthentication(newAuthentication);

        return "redirect:/user/info";
    }

    /**
     * 패스워드 찾기 뷰
     */
    @GetMapping("/findPassword")
    public String findPasswordGet() {
        return "contents/auth/findPasswordView";
    }

}
