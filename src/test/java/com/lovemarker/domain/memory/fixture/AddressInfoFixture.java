package com.lovemarker.domain.memory.fixture;

import com.lovemarker.domain.memory.vo.AddressInfo;
import org.locationtech.jts.geom.GeometryFactory;
import org.locationtech.jts.geom.Point;
import org.locationtech.jts.geom.PrecisionModel;

public class AddressInfoFixture {

    private static final GeometryFactory geometryFactory = new GeometryFactory(new PrecisionModel(), 4326);
    private static final String ADDRESS = "서울특별시 강남구 역삼동 132";
    private static final Point position = geometryFactory.createPoint(new org.locationtech.jts.geom.Coordinate(3.1, 2.2));

    public static AddressInfo addressInfo() {
        return new AddressInfo(ADDRESS, position);
    }

    public static String getAddress() {
        return ADDRESS;
    }

    public static Point getPosition() {
        return position;
    }
}
