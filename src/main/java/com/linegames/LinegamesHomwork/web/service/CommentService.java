package com.linegames.LinegamesHomwork.web.service;

import com.linegames.LinegamesHomwork.web.model.Comment;
import com.linegames.LinegamesHomwork.web.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link Comment} 클래스에 대한 서비스
 */
@Service
public class CommentService {
    @Autowired
    private CommentRepository commentRepository;

    /**
     * {@link Comment} 객체를 저장
     *
     * @param comment 저장할 {@link Comment} 객체
     * @return 저장된 {@link Comment} 객체
     */
    public Comment save(Comment comment) {
        return commentRepository.save(comment);
    }

    /**
     * {@link Comment} 객체를 삭제
     *
     * @param comment 삭제할 {@link Comment} 객체
     */
    public void delete(Comment comment) {
        commentRepository.delete(comment);
    }

    /**
     * id에 매치되는 {@link Comment} 객체를 반환
     *
     * @param commentId 반환할 {@link Comment}객체의 id값
     * @return id에 매치되는 {@link Comment} 객체
     */
    public Comment findById(Long commentId) {
        return commentRepository.findById(commentId).orElse(null);
    }
}
