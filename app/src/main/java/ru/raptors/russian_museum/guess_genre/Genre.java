package ru.raptors.russian_museum.guess_genre;

import android.content.res.Resources;

import androidx.annotation.NonNull;

import ru.raptors.russian_museum.R;

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
    public static int getTotal() {
        return 14;
    }
    private Genre(int value) {
        this.value = value;
    }
    public int getValue() {
        return value;
    }

    public String getString(Resources resources) {
        return resources.getStringArray(R.array.genres)[value];
        /*switch (this) {
            case Abstraction:
                return "Абстракция";
            case Allegory:
                return "Аллегория";
            case Animalism:
                return "Анималистика";
            case BattleGenre:
                return "Батальный жанр";
            case BiblicalStory:
                return "Библейский сюжет";
            case HouseholdGenre:
                return "Бытовой жанр";
            case Illustration:
                return "Иллюстрация";
            case Interior:
                return "Интерьер";
            case HistoricalPainting:
                return "Историческая живопись";
            case Caricature:
                return "Карикатура";
            case MythologicalStory:
                return "Мифологический сюжет";
            case StillLife:
                return "Натюрморт";
            case Landscape:
                return "Пейзаж";
            case Portrait:
                return "Портрет";
            default:
                return "";
        }*/
    }

    public String getDescription(Resources resources) {
        return resources.getStringArray(R.array.genres_description)[value];
    }

    public static Genre getInstance(int value) throws EnumConstantNotPresentException {
        switch (value) {
            case 0:
                return Abstraction;
            case 1:
                return Allegory;
            case 2:
                return Animalism;
            case 3:
                return BattleGenre;
            case 4:
                return BiblicalStory;
            case 5:
                return HouseholdGenre;
            case 6:
                return Illustration;
            case 7:
                return Interior;
            case 8:
                return HistoricalPainting;
            case 9:
                return Caricature;
            case 10:
                return MythologicalStory;
            case 11:
                return StillLife;
            case 12:
                return Landscape;
            case 13:
                return Portrait;
            default:
                throw new EnumConstantNotPresentException(Genre.class, "Wrong number of genre");
        }
    }
}