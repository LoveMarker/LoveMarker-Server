package com.lovemarker.domain.memory.repository;

import com.lovemarker.domain.memory.Memory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemoryRepository extends JpaRepository<Memory, Long> {

}
