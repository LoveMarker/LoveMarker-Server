package com.lovemarker.domain.user.repository;

import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.vo.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    long countByCouple_CoupleId(Long coupleId);
    Optional<User> findBySocialToken_SocialTokenAndProvider(String socialToken, SocialType provider);
    boolean existsBySocialToken_SocialTokenAndProvider(String socialToken, SocialType provider);

}
