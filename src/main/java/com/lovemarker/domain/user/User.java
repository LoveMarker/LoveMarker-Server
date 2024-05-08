package com.lovemarker.domain.user;

import com.lovemarker.domain.common.BaseTimeEntity;
import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.user.vo.SocialToken;
import com.lovemarker.domain.user.vo.SocialType;
import com.lovemarker.domain.user.vo.UserNickname;
import jakarta.persistence.Column;
import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseTimeEntity {

    @Id
    @Column(name = "user_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;

    @Embedded
    private UserNickname nickname;

    @Column(name = "provider")
    @Enumerated(EnumType.STRING)
    private SocialType provider;

    @Embedded
    private SocialToken socialToken;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "couple_id")
    private Couple couple;

    public User(String nickname, String provider, String socialToken) {
        this.nickname = new UserNickname(nickname);
        this.provider = SocialType.from(provider);
        this.socialToken = new SocialToken(socialToken);
    }

    public void makeCouple(Couple couple) {
        this.couple = couple;
    }

    public Long getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname.getNickname();
    }

    public Couple getCouple() {
        return couple;
    }
}