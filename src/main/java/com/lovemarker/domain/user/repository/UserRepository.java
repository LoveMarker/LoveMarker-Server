package com.lovemarker.domain.user.repository;

import com.lovemarker.domain.user.User;
import com.lovemarker.domain.user.vo.SocialType;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends JpaRepository<User, Long> {

    long countByCouple_CoupleId(Long coupleId);
    Optional<User> findBySocialToken_SocialTokenAndProvider(String socialToken, SocialType provider);
    boolean existsBySocialToken_SocialTokenAndProvider(String socialToken, SocialType provider);
    boolean existsByNickname_Nickname(String nickname);

    @Query("SELECT u FROM User u JOIN FETCH u.couple c "
        + "WHERE c.coupleId = :coupleId "
        + "AND u.userId != :userId")
    Optional<User> findPartnerUser(@Param("coupleId") Long coupleId, @Param("userId") Long userId);

}
