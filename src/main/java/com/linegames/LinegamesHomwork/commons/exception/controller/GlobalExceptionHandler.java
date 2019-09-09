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

    // API Exception Handling
    @ExceptionHandler(APIException.class)
    @ResponseBody
    public APIExceptionResponse handleAPIException(APIException ex) {
        APIExceptionResponse exceptionResponse = new APIExceptionResponse();
        exceptionResponse.setErrorCode(ex.getErrorCodeEmum().getErrorCode());
        exceptionResponse.setErrorMessage(ex.getErrorCodeEmum().getErrorMessage());
        return exceptionResponse;
    }

    // WEB Exception Handling
    @ExceptionHandler(WebException.class)
    public String handleWebException(WebException ex) {
        return "redirect:/" + ex.getRedirectURL();
    }
}
