package com.linegames.LinegamesHomwork.auth.controller;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import com.linegames.LinegamesHomwork.commons.APIResponse;
import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEnum;
import com.linegames.LinegamesHomwork.commons.exception.api.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

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
            throw new APIException(ErrorCodeEnum.USER_NOT_EXIST);
        }

        APIResponse apiResponse = new APIResponse();
        apiResponse.add("username", customUserDetails.getUsername());

        return apiResponse;
    }

    /**
     * 패스워드 찾기 검증 로직
     */
    @PutMapping("/password/{username}")
    public APIResponse passwordPut(
            @PathVariable("username") String username, @RequestBody CustomUserDetails customUserDetails) {
        CustomUserDetails retrievedUser = userDetailsService.loadUserByUsername(username);

        if ( retrievedUser == null )
            throw new APIException(ErrorCodeEnum.USER_NOT_EXIST);

        retrievedUser.setPassword(passwordEncoder.encode(customUserDetails.getPassword()));
        userDetailsService.save(retrievedUser);

        APIResponse apiResponse = new APIResponse();
        apiResponse.add("username", retrievedUser.getUsername());

        return apiResponse;
    }
}
