package com.linegames.LinegamesHomwork.web.model;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

/**
 * {@link org.springframework.data.domain.PageRequest}를 커스터마이징하는 클래스
 */
public class CustomPageRequest {
    private int page;
    private int size;

    private Sort.Direction direction;

    public CustomPageRequest setPage(int page) {
        this.page = (page <= 0) ? 1 : page;
        return this;
    }

    public CustomPageRequest setSize(int size) {
        int DEFAULT_SIZE = 5;   // 기본 사이즈
        int MAX_SIZE = 10;      // 최대 사이즈
        int MIN_SIZE = 1;       // 최소 사이즈

        this.size = (size > MAX_SIZE) || (size < MIN_SIZE) ? DEFAULT_SIZE : size;
        return this;
    }

    public CustomPageRequest setDirection(Sort.Direction direction) {
        this.direction = direction;
        return this;
    }

    public PageRequest of() {
        return PageRequest.of(page - 1, size, direction, "addDate");
    }
}
