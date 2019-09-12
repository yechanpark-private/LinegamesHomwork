package com.linegames.LinegamesHomwork.web.controller.post;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEnum;
import com.linegames.LinegamesHomwork.commons.exception.web.WebException;
import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.model.Post;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import com.linegames.LinegamesHomwork.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

/**
 * 게시글 핸들링 컨트롤러
 */
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private PostService postService;

    @GetMapping("/{postId}")
    public String getPostGet(@PathVariable("postId") Long postId, Model model) throws WebException {
        Post post = postService.findById(postId);

        if (post == null)
            throw new WebException(ErrorCodeEnum.POST_NOT_EXIST.getErrorMessage(), "/");

        model.addAttribute("post", post);
        Board board = post.getBoard();
        model.addAttribute("board", board);

        return "contents/web/post/postView";
    }

    /**
     * 게시글 등록 뷰
     */
    @GetMapping("/add/{boardURI}")
    public String addPostGet(@PathVariable("boardURI") String boardURI, Model model) throws WebException {
        Board board = boardService.findByBoardURI(boardURI);

        if (board == null)
            throw new WebException(ErrorCodeEnum.BOARD_NOT_EXIST.getErrorMessage(), "/");

        Post post = new Post();
        model.addAttribute("post", post);
        model.addAttribute("board", board);

        return "contents/web/post/editView";
    }

    /**
     * 게시글 추가, 수정 로직
     */
    @PostMapping("/add/{boardURI}")
    public String addPostPost(
            @PathVariable("boardURI") String boardURI,
            @ModelAttribute Post post, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Board board = boardService.findByBoardURI(boardURI);

        Post newPost;

        // post.id 값이 없으면 새로운 Post 객체 : 게시글 추가
        if (post.getId() == null) {
            newPost = new Post();
            newPost.setAddDate(LocalDateTime.now());
            newPost.setAuthor(customUserDetails.getUsername());
            newPost.setBoard(board);
        }

        // post.id가 있는 경우 실제 게시글 조회 : 게시글 수정
        else {
            newPost = postService.findById(post.getId());

            // 실제 게시글이 없는 경우 : 게시글 추가
            if( newPost == null) {
                newPost = new Post();
                newPost.setAddDate(LocalDateTime.now());
                newPost.setAuthor(customUserDetails.getUsername());
                newPost.setBoard(board);
            }
        }

        newPost.setTitle(post.getTitle());
        newPost.setContent(post.getContent());
        newPost.setLastModifyDate(LocalDateTime.now());

        postService.save(newPost);

        return "redirect:/board/get/" + boardURI;
    }

    /**
     * 게시글 수정 뷰
     */
    @GetMapping("/edit/{postId}")
    public String editPostGet(@PathVariable("postId") Long postId, Model model) throws WebException {
        Post post = postService.findById(postId);

        if (post == null) {
            throw new WebException(ErrorCodeEnum.POST_NOT_EXIST.getErrorMessage(), "/");
        }

        model.addAttribute("post", post);
        model.addAttribute("board", post.getBoard());

        return "contents/web/post/editView";
    }
}
