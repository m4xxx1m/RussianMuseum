package ru.raptors.russian_museum;

public enum DifficultyLevel {
    Under8(0),
    Under14(1),
    Over14(2);

    private final int value;
    private DifficultyLevel(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
    public static DifficultyLevel getInstance(int value) {
        switch (value) {
            case 0:
                return Under8;
            case 1:
                return Under14;
            default:
                return Over14;
        }
    }
}
