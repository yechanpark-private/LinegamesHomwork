package com.linegames.LinegamesHomwork.web.controller.post;

import com.linegames.LinegamesHomwork.commons.APIResponse;
import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEnum;
import com.linegames.LinegamesHomwork.commons.exception.api.APIException;
import com.linegames.LinegamesHomwork.web.model.Post;
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
    private PostService postService;

    /**
     * postId에 해당하는 Post를 삭제하는 api
     *
     * @param postId 삭제할 Post의 id
     * @return APIResponse.data.post 삭제된 Post의 정보
     *         APIResponse.data.boardURI 삭제된 Post가 포함된 Board의 URI Path
     */
    @DeleteMapping("/{postId}")
    public APIResponse deletePost(@PathVariable("postId") Long postId) {
        Post post = postService.findById(postId);

        if (post == null) {
            throw new APIException(ErrorCodeEnum.POST_NOT_EXIST);
        }

        APIResponse apiResponse = new APIResponse();
        apiResponse.add("post", post);

        Post currentPost = post;

        // boardURI가 나올 때 까지 부모 Post를 올라가며 boardURI를 찾음
        while (true) {

            // 현재 Post가 Board의 자식인 경우 BoardURI를 세팅 후 break
            if (currentPost.getBoard() != null) {
                apiResponse.add("boardURI", currentPost.getBoard().getBoardURI());
                break;
            }

            // board가 없으면 부모 Post를 세팅
            currentPost = currentPost.getParent();
        }

        postService.delete(post);

        return apiResponse;
    }

}
