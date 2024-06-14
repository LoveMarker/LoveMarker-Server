package com.lovemarker.base;

import com.lovemarker.domain.couple.repository.CoupleRepository;
import com.lovemarker.domain.memory.repository.MemoryRepository;
import com.lovemarker.domain.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

@DataJpaTest
public abstract class BaseRepositoryTest {

    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected UserRepository userRepository;

    @Autowired
    protected CoupleRepository coupleRepository;

    @Autowired
    protected MemoryRepository memoryRepository;
}
