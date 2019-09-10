package com.linegames.LinegamesHomwork.web.service;

import com.linegames.LinegamesHomwork.web.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 게시글 서비스
 */
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;
}
