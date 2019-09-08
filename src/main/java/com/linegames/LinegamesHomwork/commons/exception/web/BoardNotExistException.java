package com.linegames.LinegamesHomwork.commons.exception.web;

/**
 * 게시판이 없을 때 던지는 Exception
 */
public class BoardNotExistException extends WebException {
    public BoardNotExistException(String errorMessage, String redirectURL) {
        super(errorMessage, redirectURL);
    }
}
