package com.linegames.LinegamesHomwork.web.repository;

import com.linegames.LinegamesHomwork.web.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 게시글 Repository
 */
@Repository
public interface PostRepository extends JpaRepository<Post, Long> {
}
