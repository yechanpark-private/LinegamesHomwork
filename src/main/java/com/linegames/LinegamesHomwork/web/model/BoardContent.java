package com.linegames.LinegamesHomwork.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 실제 게시글 클래스
 */
@Data
@Entity
public class BoardContent {
    @Id
    @GeneratedValue
    @Column(name = "CONTENT_ID")
    private Long boardContentId;

    private String author;  // 작성한 유저 이름

    private String title; // 게시글 제목

    @Column(length = 40000)
    private String content; // 게시글 내용

    private LocalDateTime addDate;        // 작성시간
    private LocalDateTime lastModifyDate; // 최종 수정 시간

    /**
     * {@link JoinColumn}        : name 값은 현재 클래스가 DB ROW에 저장될 때, 상대 클래스에 대한 외래키 값을 포함하는 컬럼명.
     * 상대 클래스에 대한 외래키 값을 포함하는 컬럼명이기 때문에 상대 클래스의 정보가 기술되어야 한다.
     * {@link ManyToOne}         : 현재 클래스(BoardContent)와 상대 클래스(Baord)가 N:1일 때 선언.
     * {@link JsonBackReference} : JSON Serialize 시 상대 클래스(Board)의 정보를 Serialize 하지 않는다. (참고 : {@link JsonManagedReference} )
     */
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonBackReference
    private Board board; // 이 게시물을 포함하는 게시판

    // 부모 게시글
    @ManyToOne
    @JsonBackReference
    private BoardContent parentBoardContent;

    // 자식 게시글 리스트
    @OneToMany(mappedBy = "parentBoardContent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE})
    @JsonManagedReference
    private List<BoardContent> childrenBoardContentList = new ArrayList<>();
}