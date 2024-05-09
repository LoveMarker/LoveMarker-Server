package com.lovemarker.domain.memory.vo;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchException;

import com.lovemarker.global.exception.BadRequestException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.locationtech.jts.geom.Coordinate;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

class AddressInfoTest {

    @Nested
    @DisplayName("AddressInfo 생성 시")
    class NewAddressInfoTest {

        GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);

        @Test
        @DisplayName("성공")
        void newAddressInfo() {
            //given
            String address = "서울시 강남구 역삼동 121";
            Point position = geometryFactory.createPoint(new Coordinate(3.1, 2.2));

            //when
            AddressInfo addressInfo = new AddressInfo(address, position);

            //then
            assertThat(addressInfo.getAddress()).isEqualTo(address);
            assertThat(addressInfo.getPosition()).isEqualTo(position);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 null")
        void exceptionWhenAddressIsNull() {
            //given
            String address = null;
            Point position = geometryFactory.createPoint(new Coordinate(3.1, 2.2));

            //when
            Exception exception = catchException(() -> new AddressInfo(address, position));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }

        @Test
        @DisplayName("예외(BadRequestException): 입력값이 blank")
        void exceptionWhenAddressIsBlank() {
            //given
            String address = "";
            Point position = geometryFactory.createPoint(new Coordinate(3.1, 2.2));

            //when
            Exception exception = catchException(() -> new AddressInfo(address, position));

            //then
            assertThat(exception).isInstanceOf(BadRequestException.class);
        }
    }
}