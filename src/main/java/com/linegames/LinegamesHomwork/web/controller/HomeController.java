package com.linegames.LinegamesHomwork.web.controller;

import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

/**
 * 홈 컨트롤러
 */
@Controller
@RequestMapping("/")
public class HomeController {

    @Autowired
    private BoardService boardService;

    /**
     * 게시판 진입부, 존재하는 게시판의 목록을 보여주는 뷰를 반환
     */
    @GetMapping("")
    public String home(Model model) {
        List<Board> boardList = boardService.findAll();
        model.addAttribute("boardList", boardList);
        return "contents/web/home";
    }
}
