package com.linegames.LinegamesHomwork.web.controller.post;

import com.linegames.LinegamesHomwork.commons.APIResponse;
import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEnum;
import com.linegames.LinegamesHomwork.commons.exception.api.APIException;
import com.linegames.LinegamesHomwork.web.model.Post;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import com.linegames.LinegamesHomwork.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 게시글 REST 컨트롤러
 */
@RestController
@RequestMapping("/post/rest")
public class PostRestController {

    @Autowired
    private BoardService boardService;

    @Autowired
    private PostService postService;

    /**
     * 게시글 삭제 API
     */
    @DeleteMapping("/delete/{postId}")
    public APIResponse deletePost(@PathVariable("postId") Long postId) {
        Post post = postService.findById(postId);

        if (post == null) {
            throw new APIException(ErrorCodeEnum.POST_NOT_EXIST);
        }

        postService.delete(post);

        APIResponse apiResponse = new APIResponse();
        apiResponse.add("post", post);

        return apiResponse;
    }

}
