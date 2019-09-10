package com.linegames.LinegamesHomwork.commons;

import lombok.Data;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

@Data
public class APIResponse implements Serializable {
    private Map<String, Object> data = new HashMap<>();

    public APIResponse add(String key, Object data) {
        this.data.put(key, data);
        return this;
    }
}
