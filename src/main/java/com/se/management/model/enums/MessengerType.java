package com.se.management.model.enums;

public enum MessengerType {
    NOT_SET,
    ICQ,
    AIM;

    public static MessengerType of(String id) {
        return MessengerType.NOT_SET;
    }

    public String getName() {
        return "";
    }
}
