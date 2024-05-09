package com.lovemarker.domain.couple;

import static org.assertj.core.api.Assertions.assertThat;

import java.security.NoSuchAlgorithmException;
import org.junit.jupiter.api.Test;

class RandomInviteCodeGeneratorTest {

    @Test
    void generateInviteCode() throws NoSuchAlgorithmException {
        // Given
        InviteCodeStrategy generator = new RandomInviteCodeGenerator();

        // When
        String inviteCode = generator.generate();

        // Then
        assertThat(inviteCode.length()).isEqualTo(RandomInviteCodeGenerator.MAX_CODE_LENGTH);
        assertThat(inviteCode.matches("[a-z0-9]+")).isTrue();
    }
}