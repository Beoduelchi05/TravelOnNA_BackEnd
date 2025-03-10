package com.travelonna.demo.domain.user.repository;

import com.travelonna.demo.domain.user.entity.Profile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProfileRepository extends JpaRepository<Profile, Integer> {
    Optional<Profile> findByUser_UserId(Integer userId);
}
