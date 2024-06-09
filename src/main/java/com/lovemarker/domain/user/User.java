package com.lovemarker.domain.user;

import com.lovemarker.domain.common.BaseTimeEntity;
import com.lovemarker.domain.couple.Couple;
import com.lovemarker.domain.user.vo.SocialToken;
import com.lovemarker.domain.user.vo.SocialType;
import com.lovemarker.domain.user.vo.UserNickname;
import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BadRequestException;
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
@Table(name = "users")
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
        this.provider = SocialType.valueOf(provider);
        this.socialToken = new SocialToken(socialToken);
    }

    public void connectCouple(Couple couple) {
        if (this.couple != null) {
            throw new BadRequestException(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "커플 정보가 존재합니다.");
        }
        this.couple = couple;
    }

    public void updateUserNickname(String nickname) {
        this.nickname = new UserNickname(nickname);
    }

    public Long getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname.getNickname();
    }

    public String getProvider() {
        return provider.name();
    }

    public String getSocialToken() {
        return socialToken.getSocialToken();
    }

    public Couple getCouple() {
        return couple;
    }
}
