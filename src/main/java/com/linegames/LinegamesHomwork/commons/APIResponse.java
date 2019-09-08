package com.linegames.LinegamesHomwork.commons;

import lombok.Data;

import java.io.Serializable;
import java.util.Map;

@Data
public class APIResponse implements Serializable {
    private Map<String, String> data;
}
