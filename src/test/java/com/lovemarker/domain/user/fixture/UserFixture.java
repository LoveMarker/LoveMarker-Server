package com.lovemarker.domain.user.fixture;

import com.lovemarker.domain.user.User;

public class UserFixture {

    public static final String NICKNAME = "nickname";
    public static final String SOCIAL_TOKEN = "test_token";
    public static final String PROVIDER = "GOOGLE";

    public static User user() {
        return new User(NICKNAME, PROVIDER, SOCIAL_TOKEN);
    }
}
