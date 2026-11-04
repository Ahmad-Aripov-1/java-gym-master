package ru.yandex.practicum.gym;

import java.util.Comparator;

public class CounterOfTrainings {

    public static final Comparator<CounterOfTrainings> BY_COUNT_DESCENDING =
            Comparator.comparingInt(CounterOfTrainings::getCount)
                    .reversed();

    private Coach coach;
    private int count;

    public CounterOfTrainings(Coach coach, int count) {
        this.coach = coach;
        this.count = count;
    }

    public Coach getCoach() {
        return coach;
    }

    public int getCount() {
        return count;
    }
}