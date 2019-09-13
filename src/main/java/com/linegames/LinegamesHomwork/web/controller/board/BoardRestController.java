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
     * Board 리스트 전체 정보를 리턴하는 API. Board.activated 값이 false인 것은 ADMIN에게만 리턴.
     * MEMBER / ANONYMOUS : activated 값이 true인 게시판만 리턴
     * ADMIN : activated 값에 상관없이 모든 게시판 리턴
     *
     * @return 특정 권한을 가진 유저가 조회할 수 있는 Board 정보 리스트
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
     * 게시판 INSERT API - 뷰를 1개로 쓰기 때문에 편의 상 1개의 API를 사용
     */
    @PostMapping("/upsert")
    public APIResponse saveBoard(@RequestBody Board board) {

        // 기본적으로 UPDATE 모드
        APIResponse apiResponse = new APIResponse();
        apiResponse.add("upsertType", "UPDATE");

        Board updateBoard;

        // id값이 없으면 INSERT
        if (board.getId() == null) {
            updateBoard = new Board();
            apiResponse.add("upsertType", "INSERT");
        }

        // id값이 있는 경우
        else {
            updateBoard = boardService.findById(board.getId());

            // boardURI가 존재하지 않는 경우 : INSERT
            if (updateBoard == null) {
                updateBoard = new Board();
                apiResponse.add("upsertType", "INSERT");
            }
        }

        // UPSERT 공통 로직
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
