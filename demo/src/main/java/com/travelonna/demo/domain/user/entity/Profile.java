package com.travelonna.demo.domain.user.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "profile")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Profile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "profile_id")
    private Integer profileId;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "nickname")
    private String nickname;

    @Column(name = "profile_image")
    private String profileImage;

    @Column(name = "introduction")
    private String introduction;

    // 🛠 프로필 업데이트 메서드 추가
    public void updateProfile(String nickname, String profileImage, String introduction) {
        if (nickname != null) this.nickname = nickname;
        if (profileImage != null) this.profileImage = profileImage;
        if (introduction != null) this.introduction = introduction;
    }
}
