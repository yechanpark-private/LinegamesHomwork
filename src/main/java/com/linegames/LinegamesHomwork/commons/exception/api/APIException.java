package com.linegames.LinegamesHomwork.commons.exception.api;

import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEmum;
import lombok.Data;

import java.io.Serializable;

/**
 * API Exception 최상위 클래스
 */
@Data
public class APIException extends RuntimeException implements Serializable {
    private ErrorCodeEmum errorCodeEmum;

    public APIException(ErrorCodeEmum errorCodeEmum) {
        this.errorCodeEmum = errorCodeEmum;
    }
}
