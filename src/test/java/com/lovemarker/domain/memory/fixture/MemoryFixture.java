package com.lovemarker.domain.memory.fixture;

import com.lovemarker.domain.memory.Memory;
import com.lovemarker.domain.user.User;
import java.time.LocalDate;
import java.util.List;

public class MemoryFixture {

    private static final LocalDate date = LocalDate.parse("2023-02-14");
    private static final String title = "title";
    private static final String content = "content";
    private static final String address = "address";
    private static final List<String> images = List.of("url1", "url2");

    public static Memory memory(User user) {
        return new Memory(date, title, content, address, null, user, images);
    }
}
