package com.linegames.LinegamesHomwork.auth.repository;

import com.linegames.LinegamesHomwork.auth.model.CustomUserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * {@link CustomUserDetails} 클래스에 대한 Repository
 */
@Repository
public interface UserRepository extends JpaRepository<CustomUserDetails, Long> {
    CustomUserDetails findByUsername(String username);
}
