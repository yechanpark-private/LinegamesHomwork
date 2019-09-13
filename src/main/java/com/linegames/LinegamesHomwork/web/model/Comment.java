package com.linegames.LinegamesHomwork.web.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
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

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private CustomUserDetails author;

    @Column(length = 40000)
    private String content; // 댓글 내용

    // 게시글
    @ManyToOne
    @JoinColumn(name = "POST_ID")
    @JsonBackReference(value = "post_comment")
    private Post post;
}
