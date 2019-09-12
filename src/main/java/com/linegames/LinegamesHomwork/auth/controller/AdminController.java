package com.linegames.LinegamesHomwork.auth.controller;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * ADMIN만 접근할 수 있는 페이지에 대한 웹 컨트롤러
 */
@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * 게시판 관리(정보) 뷰 이동
     *
     * @param model             {@link Model}
     * @param customUserDetails 현재 로그인한 유저의 정보를 담고 있는 {@link CustomUserDetails} 객체
     * @return 게시판 정보를 수정할 수 있는 infoView 뷰 네임
     */
    @GetMapping("/board")
    public String boardManagementGet(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("customUserDetails", customUserDetails);
        return "contents/user/infoView";
    }
}
