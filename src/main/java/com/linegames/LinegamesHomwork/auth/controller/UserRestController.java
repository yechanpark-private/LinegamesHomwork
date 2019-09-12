package com.linegames.LinegamesHomwork.auth.controller;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.service.CustomUserDetailsService;
import com.linegames.LinegamesHomwork.commons.APIResponse;
import com.linegames.LinegamesHomwork.commons.exception.ErrorCodeEnum;
import com.linegames.LinegamesHomwork.commons.exception.api.APIException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

/**
 * 유저 관련 REST 컨트롤러
 */
@RestController
@RequestMapping("/user/rest/")
public class UserRestController {

    @Autowired
    private CustomUserDetailsService userDetailsService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 특정 유저의 정보를 제공하는 API
     *
     * @param username 확인할 유저의 username
     * @return APIResponse.data.username 해당 유저가 존재하는 경우 반환되는 username
     * @throws APIException 유저가 존재하지 않는 경우
     */
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
     * 패스워드 변경 API
     *
     * @param username          패스워드를 변경할 유저의 username
     * @param customUserDetails 변경될 패스워드 정보를 담은 {@link CustomUserDetails} 객체
     * @return APIResponse.data.username 패스워드가 정상적으로 변경된 username
     */
    @PutMapping("/password/{username}")
    public APIResponse passwordPut(
            @PathVariable("username") String username, @RequestBody CustomUserDetails customUserDetails) {
        CustomUserDetails retrievedUser = userDetailsService.loadUserByUsername(username);

        if (retrievedUser == null)
            throw new APIException(ErrorCodeEnum.USER_NOT_EXIST);

        retrievedUser.setPassword(passwordEncoder.encode(customUserDetails.getPassword()));
        userDetailsService.save(retrievedUser);

        APIResponse apiResponse = new APIResponse();
        apiResponse.add("username", retrievedUser.getUsername());

        return apiResponse;
    }
}
