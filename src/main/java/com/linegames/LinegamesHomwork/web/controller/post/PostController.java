package com.linegames.LinegamesHomwork.web.controller.post;

import com.linegames.LinegamesHomwork.web.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 게시글 핸들링 컨트롤러
 */
@Controller
@RequestMapping("/post")
public class PostController {

    @Autowired
    private PostService postService;
}
