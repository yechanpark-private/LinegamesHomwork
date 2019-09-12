package com.linegames.LinegamesHomwork.commons.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * {@link LocalDateTime}객체를 'yyyy-MM-dd HH:mm:ss' 포맷으로 변환하는 컨버터 클래스
 */
public class CustomLocalDateTimeFormatter {
    /**
     * {@link LocalDateTime}의 포맷을 yyyy-MM-dd HH:mm:ss 형태로 변경하여 리턴
     * @param unFormattedLocalDateTime 형태를 변경할 {@link LocalDateTime} 객체
     * @return 형태가 변경된 {@link LocalDateTime} 객체
     */
    public static LocalDateTime getFormattedLocalDateTime(LocalDateTime unFormattedLocalDateTime) {
        return LocalDateTime.parse(
                unFormattedLocalDateTime.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"))
                , DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
        );
    }

    /**
     * {@link LocalDateTime}의 포맷을 yyyy-MM-dd HH:mm:ss 형태로 변경하여 리턴
     * @param localDateTime 형태를 변경할 {@link LocalDateTime} 객체
     * @return 형태가 변경된 {@link String} 객체
     */
    public static String getFormattedString(LocalDateTime localDateTime) {
        return DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").format(localDateTime);
    }
}
