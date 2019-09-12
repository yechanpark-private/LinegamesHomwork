package com.linegames.LinegamesHomwork.web.service;

import com.linegames.LinegamesHomwork.web.model.Post;
import com.linegames.LinegamesHomwork.web.repository.PostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link Post} 클래스에 대한 서비스
 */
@Service
public class PostService {
    @Autowired
    private PostRepository postRepository;

    /**
     * 게시글에 해당하는 {@link Post} 객체 저장
     *
     * @param post 저장할 {@link Post} 객체
     * @return 저장된 {@link Post} 객체
     */
    public Post save(Post post) {
        return postRepository.save(post);
    }

    /**
     * id값에 따라 {@link Post} 객체 반환
     *
     * @param id 반환받을 {@link Post} 객체에 대한 id값
     * @return id에 매치되는 {@link Post} 객체. 없을 경우 null 반환.
     */
    public Post findById(Long id) {
        return postRepository.findById(id).orElse(null);
    }

    /**
     * {@link Post} 객체를 삭제
     *
     * @param post 삭제할 {@link Post} 객체
     */
    public void delete(Post post) {
        postRepository.delete(post);
    }
}
