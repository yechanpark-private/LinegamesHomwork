package com.linegames.LinegamesHomwork.web.controller;

import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 게시판 REST 컨트롤러
 */
@RestController
@RequestMapping("/board/rest")
public class BoardRestController {
    @Autowired
    private BoardService boardService;

    @PostMapping("/save")
    public Board save(Board board) {
        return boardService.save(board);
    }
}
