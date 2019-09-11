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
     * 게시판 추가, 수정 뷰
     */
    @GetMapping({"/upsert", "/upsert/{boardURI}"})
    public String addBoard(Model model, @PathVariable(value = "boardURI", required = false) String boardURI) {

        // boarURI 파라미터가 들어오지 않은 경우 INSERT
        if ( boardURI == null ) {
            model.addAttribute("board", new Board());
            return "contents/web/board/upsertView";
        }

        Board retrievedBoard = boardService.findByBoardURI(boardURI);

        // boardURI에 해당하는 게시판이 없는 경우 INSERT
        if ( retrievedBoard == null ) {
            model.addAttribute("board", new Board());
            return "contents/web/board/upsertView";
        }

        // boardURI에 해당하는 게시판이 있는 경우 UPDATE
        model.addAttribute("board", retrievedBoard);
        return "contents/web/board/upsertView";
    }

}
