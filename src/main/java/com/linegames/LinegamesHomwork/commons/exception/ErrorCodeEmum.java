package com.linegames.LinegamesHomwork.commons.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeEmum {
    USER_NOT_EXIST(5001, "User Not Exist"); // 유저가 존재하지 않음

    private int errorCode;
    private String errorMessage;

    ErrorCodeEmum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
