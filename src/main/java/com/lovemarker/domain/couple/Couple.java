package com.lovemarker.domain.couple;

import com.lovemarker.domain.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "couple")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Couple extends BaseTimeEntity {

    @Id
    @Column(name = "couple_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long coupleId;

    @Column(name = "anniversary", nullable = false)
    private LocalDate anniversary;

    @Column(name = "invite_code", nullable = false, unique = true)
    private String inviteCode;

    public Couple(LocalDate anniversary, String inviteCode) {
        this.anniversary = anniversary;
        this.inviteCode = inviteCode;
    }

    public Long getCoupleId() {
        return coupleId;
    }

    public LocalDate getAnniversary() {
        return anniversary;
    }

    public String getInviteCode() {
        return inviteCode;
    }
}
