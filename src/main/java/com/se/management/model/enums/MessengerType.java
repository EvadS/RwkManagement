package com.se.management.model.enums;

import java.util.stream.Stream;

public enum MessengerType {

    NOT_SET(0, "NOT_SET"),
    ICQ(1, "ICQ"),
    SKYPE(2, "S"),
    TELEGRAM(3, "TLGRM"),
    VIBER(4, "VBR"),
    AIM(5, "AIM");

    private final int id;
    private final String name;

    MessengerType(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static MessengerType of(String name) {

        return Stream.of(MessengerType.values())
                .filter(p -> p.getName().equals(name))
                .findFirst()
                .orElse(NOT_SET);
    }

    public static MessengerType of(int id) {
        return Stream.of(MessengerType.values())
                .filter(p -> p.getId() == id)
                .findFirst()
                .orElse(NOT_SET);
    }

    public String getName() {
        return this.name;
    }

    public int getId() {
        return id;
    }
}
