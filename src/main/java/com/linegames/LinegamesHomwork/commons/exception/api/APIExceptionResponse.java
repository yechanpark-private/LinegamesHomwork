package com.linegames.LinegamesHomwork.commons.exception.api;

import lombok.Data;

import java.io.Serializable;

/**
 * API 관련 Exception 발생 시 Response 모델 클래스
 */
@Data
public class APIExceptionResponse implements Serializable {
    private int errorCode;
    private String errorMessage;
}
