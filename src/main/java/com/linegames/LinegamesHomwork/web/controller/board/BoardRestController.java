package com.linegames.LinegamesHomwork.web.controller.board;

import com.linegames.LinegamesHomwork.auth.model.AuthorityEnum;
import com.linegames.LinegamesHomwork.commons.APIResponse;
import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
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

        Collection<? extends GrantedAuthority> authorities
                = SecurityContextHolder.getContext().getAuthentication().getAuthorities();

        // ADMIN 권한이 있으면 모두 리턴
        for (GrantedAuthority authority : authorities) {
            if (authority.getAuthority().equals(AuthorityEnum.ADMIN.getAuthorityName()))
                return boardService.findAll();
        }

        // ADMIN이 아닌 경우 activated만 리턴
        return boardService.findByActivated(true);
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
        if (updateBoard == null) {
            updateBoard = new Board();
            apiResponse.add("upsertType", "INSERT");
        }

        updateBoard.setBoardURI(board.getBoardURI());
        updateBoard.setTitle(board.getTitle());
        updateBoard.setActivated(board.getActivated());

        boardService.save(updateBoard);

        apiResponse
                .add("title", updateBoard.getTitle())
                .add("boardURI", updateBoard.getBoardURI());

        return apiResponse;

    }
}
