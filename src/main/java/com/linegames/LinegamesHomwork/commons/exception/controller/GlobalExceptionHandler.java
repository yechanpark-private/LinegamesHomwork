package com.linegames.LinegamesHomwork.commons.exception.controller;

import com.linegames.LinegamesHomwork.commons.exception.api.APIException;
import com.linegames.LinegamesHomwork.commons.exception.api.APIExceptionResponse;
import com.linegames.LinegamesHomwork.commons.exception.web.WebException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 발생한 모든 API, WEB Exception을 잡아서 처리하는 핸들러
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    /**
     * API Exception Handling
     *
     * @param ex 발생한 {@link APIException} 객체
     * @return 클라이언트에게 Serialize하여 리턴할 {@link APIExceptionResponse} 객체
     */
    @ExceptionHandler(APIException.class)
    @ResponseBody
    public APIExceptionResponse handleAPIException(APIException ex) {
        APIExceptionResponse exceptionResponse = new APIExceptionResponse();
        exceptionResponse.setErrorCode(ex.getErrorCodeEnum().getErrorCode());
        exceptionResponse.setErrorMessage(ex.getErrorCodeEnum().getErrorMessage());
        return exceptionResponse;
    }

    /**
     * WEB Exception Handling
     *
     * @param ex 발생한 {@link WebException} 객체
     * @return 리다이렉트 URL
     */
    @ExceptionHandler(WebException.class)
    public String handleWebException(WebException ex) {
        return "redirect:" + ex.getRedirectURL();
    }
}
