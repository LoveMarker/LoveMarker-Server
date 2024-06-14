package com.lovemarker.global.dto;

public record PageInfo(long totalElements, boolean hasNext) {

    public static PageInfo of(long totalElements, boolean hasNext) {
        return new PageInfo(totalElements, hasNext);
    }
}
