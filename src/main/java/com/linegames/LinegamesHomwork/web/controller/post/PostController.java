package com.linegames.LinegamesHomwork.web.controller.post;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEnum;
import com.linegames.LinegamesHomwork.commons.exception.web.WebException;
import com.linegames.LinegamesHomwork.commons.util.CustomLocalDateTimeFormatter;
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

    /**
     * postId에 해당하는 post의 내용을 조회하는 postView뷰로 이동
     *
     * @param model  {@link Model}
     * @param postId 조회할 Post의 id
     * @return Post 조회 뷰 이름
     */
    @GetMapping("/{postId}")
    public String getPostGet(@PathVariable("postId") Long postId, Model model) throws WebException {
        Post post = postService.findById(postId);

        if (post == null)
            throw new WebException(ErrorCodeEnum.POST_NOT_EXIST.getErrorMessage(), "/");

        model.addAttribute("post", post);

        Post currentPost = post;

        // boardURI가 나올 때 까지 부모 Post를 올라가며 boardURI를 찾음
        while (true) {

            // 현재 Post가 Board의 자식인 경우 BoardURI를 세팅 후 break
            if (currentPost.getBoard() != null) {
                model.addAttribute("redirectBoardURI", currentPost.getBoard().getBoardURI());
                break;
            }

            // board가 없으면 부모 Post를 세팅
            currentPost = currentPost.getParent();
        }

        return "contents/web/post/postView";
    }

    /**
     * boardURI에 해당하는 Board에 부모 Post를 UPSERT하는 editView뷰로 이동 (게시글 등록)
     *
     * @param model    {@link Model}
     * @param boardURI 부모 Post를 추가할 Board의 URI Path. Post.Board 객체가 있음
     * @return 부모 Post 추가, 수정 폼 뷰 네임
     */
    @GetMapping("/add/parent/{boardURI}")
    public String addParentPostGet(@PathVariable("boardURI") String boardURI, Model model) throws WebException {
        Board board = boardService.findByBoardURI(boardURI);

        if (board == null)
            throw new WebException(ErrorCodeEnum.BOARD_NOT_EXIST.getErrorMessage(), "/");

        Post post = new Post();
        post.setBoard(board);
        model.addAttribute("post", post);

        return "contents/web/post/editView";
    }

    /**
     * parentPostId에 해당하는 부모 Post에 자식 Post를 UPSERT하는 editView뷰로 이동 (답글 등록)
     *
     * @param model        {@link Model}
     * @param parentPostId 부모 Post의 id
     * @return 자식 Post 추가, 수정 폼 뷰 네임
     */
    @GetMapping("/add/child/{parentPostId}")
    public String addChildPostGet(@PathVariable("parentPostId") Long parentPostId, Model model) {
        Post parentPost = postService.findById(parentPostId);
        Post childPost = new Post();

        childPost.setParent(parentPost);

        model.addAttribute("post", childPost);

        return "contents/web/post/editView";
    }


    /**
     * postId에 해당하는 Post를 upsert하는 뷰로 이동 (게시글 수정)
     *
     * @param model  {@link Model}
     * @param postId 수정할 Post의 id
     * @return Post 추가, 수정 폼 뷰 네임
     */
    @GetMapping("/update/{postId}")
    public String editPostGet(@PathVariable("postId") Long postId, Model model) throws WebException {
        Post post = postService.findById(postId);

        if (post == null) {
            throw new WebException(ErrorCodeEnum.POST_NOT_EXIST.getErrorMessage(), "/");
        }

        model.addAttribute("post", post);
        return "contents/web/post/editView";
    }

    /**
     * boardURI에 해당하는 Board에 Post를 UPSERT하는 로직
     *
     * @param boardURI          Post를 추가할 Board의 URI Path
     * @param customUserDetails 현재 로그인한 유저의 정보
     * @param parentPost        추가할 부모 Post
     * @return Post가 추가된 Board의 postListView로 리다이렉션
     */
    @PostMapping("/add/parent/{boardURI}")
    public String addParentPostPost(
            @PathVariable("boardURI") String boardURI,
            @ModelAttribute Post parentPost, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Board board = boardService.findByBoardURI(boardURI);

        Post newParentPost;

        // Post.id 값이 없으면 새로운 Post 객체 : 게시글 추가
        if (parentPost.getId() == null) {
            newParentPost = new Post();
            newParentPost.setAddDate(LocalDateTime.now());
            newParentPost.setAuthor(customUserDetails);
            newParentPost.setBoard(board);
        }

        // post.id가 있는 경우 실제 게시글 조회 : 게시글 수정
        else {
            newParentPost = postService.findById(parentPost.getId());

            // 실제 게시글이 없는 경우 : 게시글 추가
            if (newParentPost == null) {
                newParentPost = new Post();
                newParentPost.setAddDate(LocalDateTime.now());
                newParentPost.setAuthor(customUserDetails);
                newParentPost.setBoard(board);
            }
        }

        newParentPost.setTitle(parentPost.getTitle());
        newParentPost.setContent(parentPost.getContent());
        newParentPost.setLastModifyDate(LocalDateTime.now());

        newParentPost = postService.save(newParentPost);

        return "redirect:/post/" + newParentPost.getId();
    }

    /**
     * 자식 Post 추가
     */
    @PostMapping("/add/child/{parentPostId}")
    public String addChildPostPost(
            @PathVariable("parentPostId") Long parentPostId,
            @ModelAttribute Post childPost, @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        Post parentPost = postService.findById(parentPostId);

        Post newChildPost = new Post();
        newChildPost.setTitle(childPost.getTitle());
        newChildPost.setContent(childPost.getContent());
        newChildPost.setAuthor(customUserDetails);
        newChildPost.setLastModifyDate(CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now()));
        newChildPost.setAddDate(CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now()));
        newChildPost.setParent(parentPost);

        newChildPost = postService.save(newChildPost);

        return "redirect:/post/" + newChildPost.getId();
    }
}
