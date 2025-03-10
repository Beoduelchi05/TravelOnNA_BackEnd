package com.travelonna.demo.domain.user.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.travelonna.demo.domain.user.entity.Profile;
import com.travelonna.demo.domain.user.service.ProfileService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/profile")
@RequiredArgsConstructor
public class ProfileController {

    private final ProfileService profileService;

    // 사용자 프로필 생성 또는 업데이트 API
    @PostMapping("/{userId}")
    public ResponseEntity<Profile> createOrUpdateProfile(
            @PathVariable Integer userId,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String profileImage,
            @RequestParam(required = false) String introduction) {

        Profile profile = profileService.createOrUpdateProfile(userId, nickname, profileImage, introduction);
        return ResponseEntity.ok(profile);
    }

    // 사용자 프로필 업데이트 API (분리)
    @PutMapping("/{userId}")
    public ResponseEntity<Profile> updateProfile(
            @PathVariable Integer userId,
            @RequestParam(required = false) String nickname,
            @RequestParam(required = false) String profileImage,
            @RequestParam(required = false) String introduction) {

        Profile updatedProfile = profileService.updateProfile(userId, nickname, profileImage, introduction);
        return ResponseEntity.ok(updatedProfile);
    }

    // 사용자 프로필 조회 API (Optional 제거)
    @GetMapping("/{userId}")
    public ResponseEntity<Profile> getProfile(@PathVariable Integer userId) {
        Profile profile = profileService.getProfileByUserId(userId);
        return ResponseEntity.ok(profile);
    }
}
