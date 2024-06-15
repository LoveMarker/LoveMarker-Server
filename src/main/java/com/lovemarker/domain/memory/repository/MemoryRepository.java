package com.lovemarker.domain.memory.repository;

import com.lovemarker.domain.memory.Memory;
import java.util.List;
import org.locationtech.jts.geom.Point;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface MemoryRepository extends JpaRepository<Memory, Long> {

    Page<Memory> findByCouple_CoupleIdOrderByCreatedAtDesc(Long coupleId, Pageable pageable);
    Page<Memory> findByUser_UserIdOrderByCreatedAtDesc(Long userId, Pageable pageable);

    @Query("SELECT m FROM Memory m WHERE m.couple.coupleId = :coupleId AND "
        + "ST_Dwithin(m.addressInfo.position, :point, :radius, false) = true")
    List<Memory> findByRadius(@Param("coupleId") Long coupleId, @Param("point") Point point,
        @Param("radius") int radius);
}
