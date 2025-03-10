package com.travelonna.demo.domain.user.service;

import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.travelonna.demo.domain.user.entity.User;
import com.travelonna.demo.domain.user.entity.Profile;
import com.travelonna.demo.domain.user.repository.UserRepository;
import com.travelonna.demo.domain.user.repository.ProfileRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final ProfileRepository profileRepository;

    @Transactional(readOnly = true)
    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Transactional
    public User createOrUpdateUser(String email, String name) {
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            User user = existingUser.get();
            user.updateName(name);
            return user;
        } else {
            User newUser = User.builder()
                    .email(email)
                    .name(name)
                    .build();
            userRepository.save(newUser);

            // ✅ 유저가 처음 생성될 때 자동으로 기본 프로필도 생성되도록 수정
            Profile newProfile = Profile.builder()
                    .user(newUser)
                    .nickname(name)  // 닉네임은 기본적으로 유저의 이름을 사용
                    .profileImage(null)  // 기본 프로필 이미지 없음
                    .introduction("")  // 기본 자기소개 없음
                    .build();
            profileRepository.save(newProfile);

            return newUser;
        }
    }
}
