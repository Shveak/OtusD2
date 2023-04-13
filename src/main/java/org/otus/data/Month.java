package org.otus.data;

import java.util.stream.Stream;

public enum Month {
    ЯНВАРЬ("1", "январ"),
    ФЕВРАЛЬ("2", "феврал"),
    МАРТ("3", "март"),
    АПРЕЛЬ("4", "апрел"),
    МАЙ("5", "мая"),
    ИЮНЬ("6", "июн"),
    ИЮЛЬ("7", "июл"),
    АВГУСТ("8", "август"),
    СЕНТЯБРЬ("9", "сентябр"),
    ОКТЯБРЬ("10", "октябр"),
    НОЯБРЬ("11", "ноябр"),
    ДЕКАБРЬ("12", "декабр");

    final String number;
    final String loName;

    Month(String number, String loName) {
        this.number = number;
        this.loName = loName;
    }

    public String getLoName() {
        return this.loName;
    }

    public String getNumber() {
        return this.number;
    }

    public static Month of(String loName) {
        return Stream.of(Month.values())
                .filter(x -> loName.contains(x.getLoName()))
                .findFirst()
                .orElseThrow(() -> new AssertionError(String.format("Не найден месяц по %s", loName)));
    }


}
