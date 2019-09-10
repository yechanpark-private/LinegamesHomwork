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
     * 게시판 추가 API
     */
    @PostMapping("/add")
    public APIResponse saveBoard(@RequestBody Board board) {
        Board retrievedBoard = boardService.findByBoardURI(board.getBoardURI());

        if ( retrievedBoard != null )
            throw new APIException(ErrorCodeEnum.BOARD_ADD_DUPLICATED_FAILED);

        Board newBoard = new Board();
        newBoard.setBoardURI(board.getBoardURI());
        newBoard.setTitle(board.getTitle());

        boardService.save(newBoard);

        APIResponse apiResponse = new APIResponse();
        apiResponse
                .add("title", newBoard.getTitle())
                .add("boardURI", newBoard.getBoardURI());

        return apiResponse;
    }
}
