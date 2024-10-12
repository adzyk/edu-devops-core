package ru.oa2.edu.api.domain.dto;

public enum EngineTypes {
    DOCKER ("docker"),
    DOCKER_BUILDX ("docker_buildx"),
    KUBERNETES ("kubernetes");

    private final String name;
    private EngineTypes(String name) {
        this.name = name;
    }

    public String toString() {
        return this.name;
    }

    public static EngineTypes fromString(String text) {
        for (EngineTypes t : EngineTypes.values()) {
            if (t.name.equalsIgnoreCase(text)) {
                return t;
            }
        }
        return null;
    }
}
