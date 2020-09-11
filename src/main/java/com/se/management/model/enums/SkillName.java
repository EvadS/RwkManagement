package com.se.management.model.enums;

import java.util.stream.Stream;

public enum SkillName {
    Cpp(1, "C++"),
    Java(2, "JAVA"),
    SQL(1, "SQL");

    private final int id;
    private final String name;

    SkillName(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public static SkillName of(String code) {
        return Stream.of(SkillName.values())
                .filter(p -> p.getName() == code)
                .findFirst().orElseThrow(() -> new IllegalStateException(String.format("Unsupported type %s.", code)));
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }
}
