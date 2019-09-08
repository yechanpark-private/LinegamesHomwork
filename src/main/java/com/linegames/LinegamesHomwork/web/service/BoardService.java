package com.linegames.LinegamesHomwork.web.service;

import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 게시판 서비스
 */
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    public Board findByBoardURI(String boardURI) {
        return boardRepository.findByBoardURI(boardURI);
    }

    public Board save(Board board) {
        return boardRepository.save(board);
    }

    public List<Board> findAll() {
        return boardRepository.findAll();
    }
}
