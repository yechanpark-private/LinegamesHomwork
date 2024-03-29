package com.linegames.LinegamesHomwork.commons.exception;

import lombok.Getter;

/**
 * 관리되는 오류에 대한 Enum
 */
@Getter
public enum ErrorCodeEnum {
    USER_NOT_EXIST(5001, "User Not Exist"),                                 // 유저가 존재하지 않음
    BOARD_ADD_FAILED(5102, "Board Add Failed"),                             // 게시판 추가에 실패함
    BOARD_ADD_DUPLICATED_FAILED(5103, "Board Add Failed (Duplicated URI)"), // 이미 URI가 중복되는 게시판을 추가하려고 함
    BOARD_NOT_EXIST(5104, "Board Not Exist"),                               // 업데이트하려는 게시판이 없음
    POST_NOT_EXIST(6101, "Post Not Exist");                                 // 업데이트하려는 포스트가 없음

    private int errorCode;
    private String errorMessage;

    ErrorCodeEnum(int errorCode, String errorMessage) {
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
