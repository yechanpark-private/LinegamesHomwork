package com.linegames.LinegamesHomwork.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

/**
 * 댓글 클래스
 */
@Entity
@Getter
@Setter
public class Comment {
    @Id
    @GeneratedValue
    @Column(name = "COMMENT_ID")
    private Long id;

    private String author;  // 작성한 유저 이름

    @Column(length = 40000)
    private String content; // 댓글 내용

    // 게시글
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    @JsonBackReference
    private Post post;
}
