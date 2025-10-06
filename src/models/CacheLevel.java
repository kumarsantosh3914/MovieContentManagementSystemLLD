package models;

public enum CacheLevel {
    L1("L1"),
    L2("L2"),
    PRIMARY_STORE("Primary Store");

    private final String displayName;

    CacheLevel(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
