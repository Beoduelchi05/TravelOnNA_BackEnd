package com.travelonna.demo.domain.user.service;

import com.travelonna.demo.domain.user.entity.Profile;
import com.travelonna.demo.domain.user.entity.User;
import com.travelonna.demo.domain.user.repository.ProfileRepository;
import com.travelonna.demo.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProfileService {

    private final ProfileRepository profileRepository;
    private final UserRepository userRepository;

    // 프로필 생성 또는 업데이트
    @Transactional
    public Profile createOrUpdateProfile(Integer userId, String nickname, String profileImage, String introduction) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found with ID: " + userId));

        return profileRepository.findByUser_UserId(userId)
                .map(profile -> updateExistingProfile(profile, nickname, profileImage, introduction))
                .orElseGet(() -> createNewProfile(user, nickname, profileImage, introduction));
    }

    // 기존 프로필 업데이트
    private Profile updateExistingProfile(Profile profile, String nickname, String profileImage, String introduction) {
        profile.updateProfile(nickname, profileImage, introduction);
        return profileRepository.save(profile);
    }

    // 새 프로필 생성
    private Profile createNewProfile(User user, String nickname, String profileImage, String introduction) {
        Profile profile = Profile.builder()
                .user(user)
                .nickname(nickname)
                .profileImage(profileImage)
                .introduction(introduction)
                .build();
        return profileRepository.save(profile);
    }

    // 사용자 프로필 조회 (Optional 대신 예외 처리)
    @Transactional(readOnly = true)
    public Profile getProfileByUserId(Integer userId) {
        return profileRepository.findByUser_UserId(userId)
                .orElseThrow(() -> new RuntimeException("Profile not found for User ID: " + userId));
    }
}
