package com.facility_manager.facility_reservation_manager.model;

public enum FacilityType {
    POOL("POOL"), SAUNA("SAUNA"), GYM("GYM");

    private final String name;

    private FacilityType(String value) {
        name = value;
    }

    @Override
    public String toString() {
        return name;
    }
}
