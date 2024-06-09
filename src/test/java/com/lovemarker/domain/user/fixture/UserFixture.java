package com.lovemarker.domain.user.fixture;

import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.vo.SocialType;

public class UserFixture {

    public static final String NICKNAME = "닉네임";
    public static final String SOCIAL_TOKEN = "test_token";
    public static final String PROVIDER = SocialType.GOOGLE.name();

    public static User user() {
        return new User(NICKNAME, PROVIDER, SOCIAL_TOKEN);
    }
}
