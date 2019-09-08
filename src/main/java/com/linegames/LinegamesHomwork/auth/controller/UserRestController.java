package com.linegames.LinegamesHomwork.auth.controller;

import com.linegames.LinegamesHomwork.commons.exception.api.UserNotExistException;
import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import com.linegames.LinegamesHomwork.commons.APIResponse;
import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEmum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/rest/")
public class UserRestController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @GetMapping("/{username}")
    public APIResponse getCustomUserDetails(@PathVariable("username") String username) {
        CustomUserDetails customUserDetails = userDetailsService.loadUserByUsername(username);

        if (customUserDetails == null) {
            throw new UserNotExistException(ErrorCodeEmum.USER_NOT_EXIST);
        }

        Map<String, String> apiResponseData = new HashMap<>();
        apiResponseData.put("username", customUserDetails.getUsername());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(apiResponseData);
        return apiResponse;
    }
}
