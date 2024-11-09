package com.lovemarker.domain.memory.vo;


import com.lovemarker.global.constant.ErrorCode;
import com.lovemarker.global.exception.BadRequestException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Getter
@Embeddable
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AddressInfo {

    @Column(name = "address", nullable = false)
    private String address;

    @Column(columnDefinition = "geometry(Point, 4326)")
    private Point position;

    public AddressInfo(String address, Point position) {
        validateNotNull(address);
        this.address = address;
        this.position = position;
    }

    private void validateNotNull(String address) {
        if (Objects.isNull(address) || address.isBlank()) {
            throw new BadRequestException(ErrorCode.REQUEST_VALIDATION_EXCEPTION, "주소는 필수 항목입니다.");
        }
    }
}
