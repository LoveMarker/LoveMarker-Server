package com.lovemarker.domain.auth;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

class RandomNicknameGeneratorTest {

    @Test
    void generate() throws NoSuchAlgorithmException {
        // Given
        RandomNicknameGenerator generator = new RandomNicknameGenerator();

        // When
        String nickname = generator.generate();

        // Then
        assertThat(nickname.length()).isEqualTo(RandomNicknameGenerator.MAX_NICKNAME_LENGTH);
        assertThat(nickname.matches("[a-z0-9]+")).isTrue();
    }
}
