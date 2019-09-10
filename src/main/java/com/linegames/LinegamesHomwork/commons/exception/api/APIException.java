package com.linegames.LinegamesHomwork.commons.exception.api;

import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * API Exception 최상위 클래스
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class APIException extends RuntimeException implements Serializable {
    private ErrorCodeEnum errorCodeEnum;

    public APIException(ErrorCodeEnum errorCodeEnum) {
        this.errorCodeEnum = errorCodeEnum;
    }
}
