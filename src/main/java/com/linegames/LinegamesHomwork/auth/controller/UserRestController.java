package com.linegames.LinegamesHomwork.auth.controller;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import com.linegames.LinegamesHomwork.commons.APIResponse;
import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEmum;
import com.linegames.LinegamesHomwork.commons.exception.api.UserNotExistException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/user/rest/")
public class UserRestController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

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

    /**
     * 패스워드 찾기 검증 로직
     */
    @PutMapping("/password/{username}")
    public APIResponse passwordPut(
            @PathVariable("username") String username, @RequestBody CustomUserDetails customUserDetails) {
        CustomUserDetails retrievedUser = userDetailsService.loadUserByUsername(username);

        if (retrievedUser == null )
            throw new UserNotExistException(ErrorCodeEmum.USER_NOT_EXIST);

        retrievedUser.setPassword(passwordEncoder.encode(customUserDetails.getPassword()));
        userDetailsService.save(retrievedUser);

        Map<String, String> apiResponseData = new HashMap<>();
        apiResponseData.put("username", retrievedUser.getUsername());

        APIResponse apiResponse = new APIResponse();
        apiResponse.setData(apiResponseData);
        return apiResponse;
    }
}
