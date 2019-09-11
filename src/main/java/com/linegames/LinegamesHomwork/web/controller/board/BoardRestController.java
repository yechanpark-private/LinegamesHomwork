package com.linegames.LinegamesHomwork.web.controller.board;

import com.linegames.LinegamesHomwork.commons.APIResponse;
import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEnum;
import com.linegames.LinegamesHomwork.commons.exception.api.APIException;
import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 게시판 REST 컨트롤러
 */
@RestController
@RequestMapping("/board/rest")
public class BoardRestController {

    @Autowired
    private BoardService boardService;

    /**
     * 게시판 리스트 조회 API
     */
    @GetMapping("")
    public List<Board> getBoardList() {
        return boardService.findAll();
    }

    /**
     * 게시판 UPSERT API
     */
    @PostMapping("/upsert")
    public APIResponse saveBoard(@RequestBody Board board) {
        Board updateBoard = boardService.findByBoardURI(board.getBoardURI());

        APIResponse apiResponse = new APIResponse();
        apiResponse.add("upsertType", "UPDATE");

        // boardURI가 존재하지 않는 경우 : INSERT
        if ( updateBoard == null ) {
            updateBoard = new Board();
            apiResponse.add("upsertType", "INSERT");
        }

        updateBoard.setBoardURI(board.getBoardURI());
        updateBoard.setTitle(board.getTitle());

        boardService.save(updateBoard);

        apiResponse
                .add("title", updateBoard.getTitle())
                .add("boardURI", updateBoard.getBoardURI());

        return apiResponse;

    }
}
