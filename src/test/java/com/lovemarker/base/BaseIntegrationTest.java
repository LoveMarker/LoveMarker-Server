package com.lovemarker.base;

import com.lovemarker.global.image.S3Service;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

@SpringBootTest
public abstract class BaseIntegrationTest {

    @Autowired
    DatabaseCleaner databaseCleaner;

    @MockBean
    protected S3Service s3Service;

    @BeforeEach
    void setUp() {
        databaseCleaner.clear();
    }
}
