package com.linegames.LinegamesHomwork;

import com.linegames.LinegamesHomwork.auth.model.AuthorityEnum;
import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import com.linegames.LinegamesHomwork.commons.util.CustomLocalDateTimeFormatter;
import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.model.Post;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * 데이터 초기화 클래스
 */
@Configuration
public class DataInitialClass {

    @Autowired
    private UserDetailsService userDetailsService;

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

        ((CustomUserDetailsService) userDetailsService).save(adminUserDetails);
    }

    @PostConstruct
    public void addDefaultBoard() {
        Board board = new Board();
        board.setTitle("기본게시판");
        board.setBoardURI("default");

        Post post = new Post();
        post.setAuthor("Default Post Author");
        post.setTitle("Default Post Title");
        post.setContent("Default Post Content");
        post.setAddDate(
                CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now())
        );
        post.setLastModifyDate(
                CustomLocalDateTimeFormatter.getFormattedLocalDateTime(LocalDateTime.now())
        );
        post.setBoard(board);

        // board만 저장해도 post도 같이 저장할 경우, post를 Board.postList에 추가하고, CascadeType.PERSIST 옵션을 줘야 함
        board.getPostList().add(post);

        boardService.save(board);

    }

}
