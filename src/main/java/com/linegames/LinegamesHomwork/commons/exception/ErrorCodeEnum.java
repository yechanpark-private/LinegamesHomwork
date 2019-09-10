package com.linegames.LinegamesHomwork.commons.exception;

import lombok.Getter;

@Getter
public enum ErrorCodeEnum {
    USER_NOT_EXIST(5001, "User Not Exist"),     // 유저가 존재하지 않음
    BOARD_ADD_FAILED(5102, "Board Add Failed"), // 게시판 추가에 실패함
    BOARD_ADD_DUPLICATED_FAILED(5103, "Board Add Failed (Duplicated URI)"); // 이미 URI가 중복되는 게시판을 추가하려고 함

    private int errorCode;
    private String errorMessage;

    ErrorCodeEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
