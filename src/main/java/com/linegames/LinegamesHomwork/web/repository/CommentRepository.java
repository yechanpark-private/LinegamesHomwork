package com.linegames.LinegamesHomwork.web.repository;

import com.linegames.LinegamesHomwork.web.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * 댓글 Repository
 */
@Repository
@Transactional
public interface CommentRepository extends JpaRepository<Comment, Long> {
}
