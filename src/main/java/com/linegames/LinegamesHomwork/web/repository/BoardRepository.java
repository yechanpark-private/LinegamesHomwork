package com.linegames.LinegamesHomwork.web.repository;

import com.linegames.LinegamesHomwork.web.model.Board;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 게시판 Repository
 * @see <a href="https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation">Spring Data JPA - Reference Documentation - 5.3.2. Query Creation</a>
 */
@Repository
public interface BoardRepository extends JpaRepository<Board, Long> {
    Board findByBoardURI(String boardURI);
    List<Board> findByActivated(boolean activated);
}
