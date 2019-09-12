package com.linegames.LinegamesHomwork.auth.service;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import com.linegames.LinegamesHomwork.auth.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * {@link CustomUserDetails} 클래스에 대한 Service
 */
@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    /**
     * username을 바탕으로 {@link CustomUserDetails} 객체를 리턴
     *
     * @param username 리턴받을 {@link CustomUserDetails} 객체에 대한 username
     * @return username에 매치되는 {@link CustomUserDetails} 객체
     * @throws UsernameNotFoundException username에 매치되는 {@link CustomUserDetails}객체가 없는 경우
     */
    @Override
    public CustomUserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    /**
     * {@link CustomUserDetails} 객체를 저장
     *
     * @param customUserDetails 저장할 {@link CustomUserDetails} 객체
     * @return 저장된 {@link CustomUserDetails} 객체
     */
    public CustomUserDetails save(CustomUserDetails customUserDetails) {
        return userRepository.save(customUserDetails);
    }

    /**
     * id값을 바탕으로 {@link CustomUserDetails} 객체를 리턴
     *
     * @param id 리턴받을 {@link CustomUserDetails} 객체에 대한 id
     * @return id값에 매치되는 {@link CustomUserDetails} 객체, 없을 경우 null 리턴
     */
    public CustomUserDetails findById(Long id) {
        return userRepository.findById(id).orElse(null);
    }
}
