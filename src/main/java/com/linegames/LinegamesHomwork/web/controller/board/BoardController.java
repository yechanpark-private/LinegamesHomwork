package com.linegames.LinegamesHomwork.web.controller.board;

import com.linegames.LinegamesHomwork.commons.exception.web.WebException;
import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 게시판 핸들링 컨트롤러
 */
@Controller
@RequestMapping("/board")
public class BoardController {

    @Autowired
    private BoardService boardService;

    /**
     * @param boardURI String 게시판 URI Path
     * @return viewName String 게시판 뷰
     */
    @GetMapping("/get/{boardURI}")
    public String getBoard(@PathVariable("boardURI") String boardURI, Model model) throws WebException {
        Board board = boardService.findByBoardURI(boardURI);

        if (board == null)
            throw new WebException("Board Not Exist", "/");

        model.addAttribute("board", board);
        return "contents/web/board/postListView";
    }

    /**
     * 게시판 추가 뷰
     */
    @GetMapping("/add")
    public String addBoard() {
        return "contents/web/board/addView";
    }

}
