package com.linegames.LinegamesHomwork.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시판 클래스
 */
@Data
@Entity
public class Board {
    @Id
    @Column(name = "BOARD_URI")
    private String boardURI;  // 게시판 URI

    private String boardTitle; // 게시판 제목 (유저에게 보이는 게시판 제목)

    private Boolean activated = true; // 게시판 활성화, 비활성화 여부
    //private List<GrantedAuthority> grantedAuthorityList; // 게시판에 접근할 수 있는 Authority 리스트

    /**
     * {@link OneToMany}            : 현재 클래스(Board)와 상대 클래스(BoardContent)가 1:N일 때 선언.
     * mappedBy="board"는 상대 클래스(BoardContent)에서 현재 클래스(Board)를 참조하는 변수의 이름.
     * {@link JsonManagedReference} : JSON Serialize 시 상대 클래스(BoardContent)의 정보를 Serialize한다.
     * 상대 클래스(Reply)에서는 board 변수에 {@link JsonBackReference} 를 붙여 현재 클래스(Board)의 정보를 Serialize하지 않게 해야 한다.
     * 둘 다 Infinite Recursion 오류를 해결하는데 사용한다.
     * {@link CascadeType.PERSIST} : Board 저장 시 BoardContent도 같이 저장
     * {@link CascadeType.REMOVE} : Board 삭제 시 BoardContent도 같이 삭제
     * {@link CascadeType.MERGE} : Board 업데이트 시 BoardContent도 같이 업데이트
     */
    @OneToMany(mappedBy = "board", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JsonManagedReference
    private List<BoardContent> boardContentList = new ArrayList<>(); // 해당 게시판이 포함하는 게시글
}
