package com.lovemarker.domain.couple.repository;

import com.lovemarker.domain.couple.Couple;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoupleRepository extends JpaRepository<Couple, Long> {

    Optional<Couple> findByInviteCode(String inviteCode);
}
