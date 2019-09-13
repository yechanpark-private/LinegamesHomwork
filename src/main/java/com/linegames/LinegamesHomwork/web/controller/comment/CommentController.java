package com.linegames.LinegamesHomwork.web.controller.comment;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.web.model.Comment;
import com.linegames.LinegamesHomwork.web.model.Post;
import com.linegames.LinegamesHomwork.web.service.CommentService;
import com.linegames.LinegamesHomwork.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 댓글 핸들링 컨트롤러
 */
@Controller
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @Autowired
    private PostService postService;

    /**
     * 댓글 추가 로직
     *
     * @param postId            댓글을 추가할 게시글({@link com.linegames.LinegamesHomwork.web.model.Post}의 id값
     * @param comment           postId에 매치되는 {@link com.linegames.LinegamesHomwork.web.model.Post}객체에 추가할 댓글({@link Comment}) 객체
     * @param customUserDetails 현재 로그인한 유저의 정보를 담은 {@link CustomUserDetails} 정보
     * @return 댓글이 추가된 게시글 리다이렉션 URI
     */
    @PostMapping("/{postId}")
    public String addCommentPost(
            @PathVariable("postId") Long postId,
            @ModelAttribute("comment") Comment comment,
            @AuthenticationPrincipal CustomUserDetails customUserDetails) {

        // 로그인 하지 않았으면 게시글로 리다이렉트
        if (customUserDetails == null) {
            return "redirect:/post/" + postId;
        }

        Post post = postService.findById(postId);
        Comment newComment = new Comment();
        newComment.setPost(post);
        newComment.setContent(comment.getContent());
        newComment.setAuthor(customUserDetails.getUsername());
        post.getComments().add(newComment);

        postService.save(post);
        return "redirect:/post/" + post.getId();
    }
}
