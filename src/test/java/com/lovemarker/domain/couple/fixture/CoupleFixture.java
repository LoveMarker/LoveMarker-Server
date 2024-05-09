package com.lovemarker.domain.couple.fixture;

import com.lovemarker.domain.couple.Couple;
import java.time.LocalDate;

public class CoupleFixture {

    private static final LocalDate ANNIVERSARY = LocalDate.now();
    private static final String INVITE_CODE = "inviteCode";

    public static Couple couple() {
        return new Couple(ANNIVERSARY, INVITE_CODE);
    }

}
