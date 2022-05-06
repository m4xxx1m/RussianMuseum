package ru.raptors.russian_museum.guess_genre;

public enum Genre {
    Abstraction(0),
    Allegory(1),
    Animalism(2),
    BattleGenre(3),
    BiblicalStory(4),
    HouseholdGenre(5),
    Illustration(6),
    Interior(7),
    HistoricalPainting(8),
    Caricature(9),
    MythologicalStory(10),
    StillLife(11),
    Landscape(12),
    Portrait(13);

    private final int value;
    private Genre(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }
}
