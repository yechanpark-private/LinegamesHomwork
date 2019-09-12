package com.linegames.LinegamesHomwork.web.service;

import com.linegames.LinegamesHomwork.web.model.Post;
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

    public Post save(Post post) {
        return postRepository.save(post);
    }

    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }
}
