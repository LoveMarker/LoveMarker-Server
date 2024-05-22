package com.lovemarker.domain.auth.service;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.lovemarker.domain.auth.dto.response.SocialInfoResponse;
import com.lovemarker.domain.auth.exception.UnauthorizedException;
import com.lovemarker.global.constant.ErrorCode;
import java.util.Collections;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GoogleSignInService implements SocialSignInService {

    @Value("${google.client-id}")
    private String CLIENT_ID;

    @Override
    public SocialInfoResponse getSocialInfo(String token) {
        HttpTransport transport = new NetHttpTransport();
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();

        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
            .setAudience(Collections.singletonList(CLIENT_ID))
            .build();

        String userId = null;
        String email = null;
        try {
            GoogleIdToken idToken = verifier.verify(token);
            GoogleIdToken.Payload payload = idToken.getPayload();
            userId = payload.getSubject();
            email = payload.getEmail();
        } catch (Exception e) {
            throw new UnauthorizedException(ErrorCode.INVALID_GOOGLE_ID_TOKEN_EXCEPTION,
                ErrorCode.INVALID_GOOGLE_ID_TOKEN_EXCEPTION.getMessage());
        }
        return SocialInfoResponse.of(email, userId);
    }
}
