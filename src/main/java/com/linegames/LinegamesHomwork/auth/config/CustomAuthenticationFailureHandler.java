package com.linegames.LinegamesHomwork.auth.config;

import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * <table border="1">
 *     <tr>
 *         <td>Exception</td>
 *         <td>원인</td>
 *     </tr>
 *     <tr>
 *         <td>BadCredentialException</td>
 *         <td>비밀번호가 일치하지 않은 경우</td>
 *     </tr>
 *     <tr>
 *         <td>InternalAuthenticationServiceException</td>
 *         <td>존재 하지 않는 아이디일 경우</td>
 *     </tr>
 *     <tr>
 *         <td>AthenticationCredentialNotFoundException</td>
 *         <td>인증 요구가 거부 됐을 경우</td>
 *     </tr>
 *     <tr>
 *         <td>LockedException</td>
 *         <td>인증 거부 : 잠긴 계정일 경우</td>
 *     </tr>
 *     <tr>
 *         <td>DisabledException</td>
 *         <td>인증 거부 : 계정 비활성화</td>
 *     </tr>
 *     <tr>
 *         <td>AccountExpiredException</td>
 *         <td>인증 거부 : 계정 유효 기간 만료</td>
 *     </tr>
 *     <tr>
 *         <td>CredentialExpiredException</td>
 *         <td>인증 거부 : 비밀번호 유효 기간 만료</td>
 *     </tr>
 * </table>
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {

        // 비밀번호가 틀린 경우
        if (exception instanceof BadCredentialsException) {
            request.setAttribute("login_validation_error_password", "패스워드가 맞지 않습니다.");
        }

        // 아이디가 존재하지 않는 경우
        else if (exception instanceof InternalAuthenticationServiceException) {
            request.setAttribute("login_validation_error_username", "아이디가 존재하지 않습니다.");
        }

        request.setAttribute("username", request.getParameter("username"));

        request.getRequestDispatcher("/auth/login").forward(request, response);
    }

}
