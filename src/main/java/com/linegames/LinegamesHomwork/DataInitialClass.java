package com.linegames.LinegamesHomwork;

import com.linegames.LinegamesHomwork.auth.model.AuthorityEnum;
import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import com.linegames.LinegamesHomwork.web.model.Board;
import com.linegames.LinegamesHomwork.web.model.BoardContent;
import com.linegames.LinegamesHomwork.web.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.annotation.PostConstruct;
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
        board.setBoardTitle("기본게시판");
        board.setBoardURI("default");

        BoardContent boardContent1 = new BoardContent();
        boardContent1.setAuthor("author1");
        boardContent1.setTitle("title");
        boardContent1.setContent("content");
        boardContent1.setBoard(board);

        board.getBoardContentList().add(boardContent1);

        boardService.save(board);

    }

}
