package com.lovemarker.domain.auth;

import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Random;
import org.springframework.stereotype.Component;

@Component
public class RandomNicknameGenerator {

    public static final int MAX_NICKNAME_LENGTH = 7;
    private final Random random;

    public RandomNicknameGenerator() throws NoSuchAlgorithmException {
        this.random = SecureRandom.getInstanceStrong();
    }

    public String generate() {
        return createRandomString();
    }

    private String createRandomString() {
        StringBuilder randomBuf = new StringBuilder();
        for (int i = 0; i < MAX_NICKNAME_LENGTH; i++) {
            if (random.nextBoolean()) {
                randomBuf.append((char)((random.nextInt(26)) + 97));
            } else {
                randomBuf.append(random.nextInt(10));
            }
        }
        return randomBuf.toString();
    }
}
