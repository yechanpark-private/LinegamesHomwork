package com.linegames.LinegamesHomwork.web.controller.board;

import com.linegames.LinegamesHomwork.commons.exception.web.WebException;
import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.model.CustomPageRequest;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import com.linegames.LinegamesHomwork.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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

    @Autowired
    private PostService postService;

    /**
     * boarURI에 해당하는 Board에 대한 게시글 리스트로 이동
     *
     * @param boardURI String 게시판 URI Path
     * @param model    {@link Model}
     * @param pageable 페이징 정보를 포함하는 {@link Pageable} 정보. 기본 페이징 정보를 포함한다.
     * @return 게시판에 존재하는 게시글 리스트 뷰 네임
     */
    @GetMapping("/{boardURI}")
    public String getBoard(
            @PathVariable("boardURI") String boardURI,
            Model model,
            @PageableDefault(size = 2, page = 1, sort = "addDate", direction = Sort.Direction.DESC) Pageable pageable
    ) throws WebException {
        Board board = boardService.findByBoardURI(boardURI);

        if (board == null)
            throw new WebException("Board Not Exist", "/");

        int pageNumber = pageable.getPageNumber();
        int pageSize = pageable.getPageSize();

        CustomPageRequest customPageRequest
                = new CustomPageRequest()
                .setPage(pageNumber)
                .setSize(pageSize)
                .setDirection(Sort.Direction.DESC);

        model.addAttribute("board", board);
        model.addAttribute("parentPostList", postService.findAllByBoardId(board.getId(), customPageRequest.of()));

        // 보여줄 페이징 번호 최대 갯수
        model.addAttribute("pageBarSize", 5);
        return "contents/web/board/postListView";
    }

    /**
     * 새로운 Board를 추가하는 upserView 뷰로 이동
     *
     * @param model {@link Model}
     * @return 게시판 추가/수정 뷰 네임
     */
    @GetMapping("/add")
    public String addBoard(Model model) {
        Board board = new Board();
        model.addAttribute("board", board);
        return "contents/web/board/upsertView";
    }

    /**
     * boardURI에 해당하는 Board를 수정하는 upsertView로 뷰로 이동
     *
     * @param boardURI 수정할 게시판에 대한 URI Path
     * @param model    {@link Model}
     * @return 게시판 추가/수정 뷰 네임
     */
    @GetMapping("/update/{boardURI}")
    public String editBoard(Model model, @PathVariable(value = "boardURI") String boardURI) {
        Board board = boardService.findByBoardURI(boardURI);
        model.addAttribute("board", board);
        return "contents/web/board/upsertView";
    }

}
