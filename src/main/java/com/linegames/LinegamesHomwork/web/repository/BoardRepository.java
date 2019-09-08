package com.linegames.LinegamesHomwork.web.repository;

import com.linegames.LinegamesHomwork.web.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * 게시판 Repository
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByBoardURI(String boardURI);
}
