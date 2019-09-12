package com.linegames.LinegamesHomwork.commons.exception.web;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * Web Exception 최상위 클래스
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class WebException extends Exception implements Serializable {
    private String errorMessage;
    private String redirectURL;

    public WebException(String errorMessage, String redirectURL) {
        this.errorMessage = errorMessage;
        this.redirectURL = redirectURL;
    }
}
