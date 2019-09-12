package com.linegames.LinegamesHomwork.web.service;

import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * {@link Board} 클래스에 대한 Service
 */
@Service
public class BoardService {

    @Autowired
    private BoardRepository boardRepository;

    /**
     * boardURI에 매치되는 {@link Board} 객체를 리턴
     *
     * @param boardURI 반환하려는 게시판의 URI
     * @return boardURI와 매치되는 {@link Board} 객체
     */
    public Board findByBoardURI(String boardURI) {
        return boardRepository.findByBoardURI(boardURI);
    }

    /**
     * {@link Board} 객체를 저장
     *
     * @param board 저장할 {@link Board} 객체
     * @return 저장된 {@link Board} 객체
     */
    public Board save(Board board) {
        return boardRepository.save(board);
    }

    /**
     * 저장된 모든 {@link Board} 객체의 리스트를 반환
     *
     * @return 저장된 모든 {@link Board} 객체의 리스트
     */
    public List<Board> findAll() {
        return boardRepository.findAll();
    }

    /**
     * 활성화 여부(Board.activated) 값에 따라 {@link Board} 객체의 리스트를 반환
     *
     * @param activated 활성화 여부
     * @return 활성화 여부에 따른 {@link Board} 객체의 리스트
     */
    public List<Board> findByActivated(boolean activated) {
        return boardRepository.findByActivated(activated);
    }
}
