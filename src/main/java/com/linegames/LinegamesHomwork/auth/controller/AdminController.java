package com.linegames.LinegamesHomwork.auth.controller;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * 게시판 관리 뷰
     */
    @GetMapping("/board")
    public String boardManagementGet(Model model, @AuthenticationPrincipal CustomUserDetails customUserDetails) {
        model.addAttribute("customUserDetails", customUserDetails);
        return "contents/user/infoView";
    }
}
