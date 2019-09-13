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
    private CommentRepository postRepository;
}
