package com.linegames.LinegamesHomwork.commons.exception.web;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

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
