package com.linegames.LinegamesHomwork;

import com.linegames.LinegamesHomwork.auth.model.AuthorityEnum;
import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import com.linegames.LinegamesHomwork.commons.util.CustomLocalDateTimeFormatter;
import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.model.Comment;
import com.linegames.LinegamesHomwork.web.model.Post;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 데이터 초기화 클래스
 */
@Configuration
public class DataInitialClass {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private BoardService boardService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * ADMIN 계정 추가
     */
    @PostConstruct
    public void addAdminAccount() {
        CustomUserDetails adminUserDetails = new CustomUserDetails();
        adminUserDetails.setAccountNonExpired(true);
        adminUserDetails.setAccountNonLocked(true);
        adminUserDetails.setCredentialsNonExpired(true);
        adminUserDetails.setEnabled(true);

        adminUserDetails.setUsername("admin");
        adminUserDetails.setPassword(passwordEncoder.encode("1234"));

        List<String> authorities = new ArrayList<>();
        authorities.add(AuthorityEnum.ADMIN.getAuthorityName());
        adminUserDetails.setAuthorities(authorities);

        userDetailsService.save(adminUserDetails);
    }

    /**
     * 게시판, 게시글 추가
     */
    @PostConstruct
    public void addDefaultBoard() {
        // 게시판 추가
        Board board = new Board();
        board.setTitle("기본게시판");
        board.setBoardURI("default");

        CustomUserDetails author = userDetailsService.loadUserByUsername("admin");
        // 부모 Post 추가
        Post post = new Post();
        post.setAuthor(author);
        post.setTitle("Default Post Title");
        post.setContent("Default Post Content");
        post.setAddDate(
                CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now())
        );
        post.setLastModifyDate(
                CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now())
        );
        board.getPostList().add(post);
        post.setBoard(board);

        // 부모 Post에 댓글1 추가
        Comment comment1 = new Comment();
        comment1.setAuthor(author);
        comment1.setContent("Default Comment1 Content");
        comment1.setPost(post);
        post.getComments().add(comment1);

        // 부모 Post에 댓글2 추가
        Comment comment2 = new Comment();
        comment2.setAuthor(author);
        comment2.setContent("Default Comment2 Content");
        comment2.setPost(post);
        post.getComments().add(comment2);

        // 자식 Post1 추가
        Post child1 = new Post();
        child1.setAuthor(author);
        child1.setTitle("Default Child1 Post Title");
        child1.setContent("Default Child1 Post Content");
        child1.setAddDate(
                CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now())
        );
        child1.setLastModifyDate(
                CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now())
        );
        post.getChildren().add(child1);
        child1.setParent(post);

        // 자식 Post2 추가
        Post child2 = new Post();
        child2.setAuthor(author);
        child2.setTitle("Default Child2 Post Title");
        child2.setContent("Default Child2 Post Content");
        child2.setAddDate(
                CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now())
        );
        child2.setLastModifyDate(
                CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now())
        );
        post.getChildren().add(child2);
        child2.setParent(post);

        // 자식 Post3 추가
        Post child3 = new Post();
        child3.setAuthor(author);
        child3.setTitle("Default Child3 Post Title");
        child3.setContent("Default Child3 Post Content");
        child3.setAddDate(
                CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now())
        );
        child3.setLastModifyDate(
                CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now())
        );
        child1.getChildren().add(child3);
        child3.setParent(child1);

        boardService.save(board);
    }

}
