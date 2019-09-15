package com.linegames.LinegamesHomwork.web.repository;

import com.linegames.LinegamesHomwork.web.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

/**
 * 게시글 Repository
 */
@Repository
@Transactional
public interface PostRepository extends JpaRepository<Post, Long> {
    Page<Post> findAllByBoardId(Long boardId, Pageable pageable);
}
