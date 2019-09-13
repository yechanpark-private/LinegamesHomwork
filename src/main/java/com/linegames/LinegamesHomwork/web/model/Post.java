package com.linegames.LinegamesHomwork.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.commons.util.CustomLocalDateTimeFormatter;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 게시글 클래스
 */
@Entity
@Getter
@Setter
public class Post {
    @Id
    @GeneratedValue
    @Column(name = "POST_ID")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private CustomUserDetails author;  // 작성한 유저 객체

    private String title; // 게시글 제목

    @Column(length = 40000)
    private String content; // 게시글 내용

    private LocalDateTime addDate;        // 작성시간
    private LocalDateTime lastModifyDate; // 최종 수정 시간

    /**
     * {@link JoinColumn}        : name 값은 현재 클래스가 DB ROW에 저장될 때, 상대 클래스에 대한 외래키 값을 포함하는 컬럼명.
     * 상대 클래스에 대한 외래키 값을 포함하는 컬럼명이기 때문에 상대 클래스의 정보가 기술되어야 한다.
     * {@link ManyToOne}         : 현재 클래스(Post)와 상대 클래스(Baord)가 N:1일 때 선언.
     * {@link JsonBackReference} : JSON Serialize 시 상대 클래스(Board)의 정보를 Serialize 하지 않는다. (참고 : {@link JsonManagedReference} )
     */
    @ManyToOne
    @JoinColumn(name = "BOARD_ID")
    @JsonBackReference(value = "board")
    private Board board; // 이 게시물을 포함하는 게시판

    // 부모 게시글
    @ManyToOne
    @JoinColumn(name = "PARENT_POST_ID")
    @JsonBackReference(value = "post")
    private Post parent;

    // 자식 게시글 리스트
    @OneToMany(mappedBy = "parent", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JsonManagedReference(value = "post")
    private List<Post> children = new ArrayList<>();

    // 댓글 리스트
    @OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    @JsonManagedReference(value = "post_comment")
    private List<Comment> comments = new ArrayList<>();

    public Post setLastModifyDate(LocalDateTime localDateTime) {
        this.lastModifyDate = CustomLocalDateTimeFormatter.getFormattedLocalDateTime(localDateTime);
        return this;
    }

    public Post setAddDate(LocalDateTime localDateTime) {
        this.addDate = CustomLocalDateTimeFormatter.getFormattedLocalDateTime(localDateTime);
        return this;
    }

    public String getLastModifyDate() {
        return CustomLocalDateTimeFormatter.getFormattedString(this.lastModifyDate);
    }

    public String getAddDate() {
        return CustomLocalDateTimeFormatter.getFormattedString(this.addDate);
    }
}
