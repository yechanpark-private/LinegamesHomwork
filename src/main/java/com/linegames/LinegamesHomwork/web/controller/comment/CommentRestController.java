package com.linegames.LinegamesHomwork.web.controller.comment;

import com.linegames.LinegamesHomwork.commons.APIResponse;
import com.linegames.LinegamesHomwork.web.model.Comment;
import com.linegames.LinegamesHomwork.web.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 댓글 REST 컨트롤러
 */
@RestController
@RequestMapping("/comment/rest")
public class CommentRestController {

    @Autowired
    private CommentService commentService;

    /**
     * commentId에 해당하는 {@link Comment} 객체의 내용을 수정하는 api
     *
     * @param commentId 수정할 {@link Comment} 객체의 id
     * @param comment   수정할 내용을 가지고 있는 {@link Comment} 객체
     * @return APIResponse.data.comment 수정된 {@link Comment} 객체의 정보
     */
    @PutMapping("/{commentId}")
    public APIResponse putComment(@PathVariable("commentId") Long commentId, @RequestBody Comment comment) {
        Comment retrievedComment = commentService.findById(commentId);
        retrievedComment.setContent(comment.getContent());
        retrievedComment = commentService.save(retrievedComment);

        APIResponse apiResponse = new APIResponse();
        apiResponse.add("comment", retrievedComment);
        apiResponse.add("postId", retrievedComment.getPost().getId());

        return apiResponse;
    }

    /**
     * commentId에 해당하는 {@link Comment} 객체를 삭제하는 api
     *
     * @param commentId 삭제할 {@link Comment} 객체의 id
     * @return APIResponse.data.comment 삭제된 {@link Comment}의 정보
     *         APIResponse.data.postId comment 삭제 후 돌아갈 게시글의 id
     */
    @DeleteMapping("/{commentId}")
    public APIResponse deleteComment(@PathVariable("commentId") Long commentId) {
        Comment comment = commentService.findById(commentId);
        commentService.delete(comment);

        APIResponse apiResponse = new APIResponse();
        apiResponse.add("comment", comment);
        apiResponse.add("postId", comment.getPost().getId());

        return apiResponse;
    }
}
