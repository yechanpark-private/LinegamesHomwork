package com.linegames.LinegamesHomwork.commons.exception.api;

import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEmum;

/**
 * 유저 검색 API 사용 시 결과가 없는 경우 던지는 Exception
 */
public class UserNotExistException extends APIException {
    public UserNotExistException(ErrorCodeEmum errorCodeEmum) {
        super(errorCodeEmum);
    }
}
