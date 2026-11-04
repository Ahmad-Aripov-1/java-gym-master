package ru.yandex.practicum.gym;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class Timetable {

    private static final Comparator<TrainingSession> BY_TIME =
            Comparator.comparing(TrainingSession::getTimeOfDay);

    // Нужен для быстрого получения всех занятий за конкретный день - O(1)
    private final Map<DayOfWeek, List<TrainingSession>> sessionsByDay =
            new HashMap<>();

    // Нужен для быстрого поиска занятий по дню и времени - O(log n)
    private final Map<DayOfWeek, TreeMap<TimeOfDay, List<TrainingSession>>> sessionsByDayAndTime =
            new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {
        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();
        TimeOfDay timeOfDay = trainingSession.getTimeOfDay();

        List<TrainingSession> sessions =
                sessionsByDay.computeIfAbsent(
                        dayOfWeek,
                        day -> new ArrayList<>()
                );

        int insertionIndex =
                Collections.binarySearch(
                        sessions,
                        trainingSession,
                        BY_TIME
                );

        if (insertionIndex < 0) {
            insertionIndex = -insertionIndex - 1;
        } else {
            while (
                    insertionIndex < sessions.size()
                            && BY_TIME.compare(
                            sessions.get(insertionIndex),
                            trainingSession
                    ) == 0
            ) {
                insertionIndex++;
            }
        }

        sessions.add(insertionIndex, trainingSession);

        sessionsByDayAndTime
                .computeIfAbsent(
                        dayOfWeek,
                        day -> new TreeMap<>()
                )
                .computeIfAbsent(
                        timeOfDay,
                        time -> new ArrayList<>()
                )
                .add(trainingSession);
    }

    public List<TrainingSession> getTrainingSessionsForDay(
            DayOfWeek dayOfWeek
    ) {
        return sessionsByDay.getOrDefault(
                dayOfWeek,
                Collections.emptyList()
        );
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek,
            TimeOfDay timeOfDay
    ) {
        TreeMap<TimeOfDay, List<TrainingSession>> sessionsByTime =
                sessionsByDayAndTime.get(dayOfWeek);

        if (sessionsByTime == null) {
            return Collections.emptyList();
        }

        return sessionsByTime.getOrDefault(
                timeOfDay,
                Collections.emptyList()
        );
    }

    public List<CounterOfTrainings> getCountByCoaches() {
        Map<Coach, Integer> countByCoach = new HashMap<>();

        for (List<TrainingSession> sessions : sessionsByDay.values()) {
            for (TrainingSession session : sessions) {
                Coach coach = session.getCoach();

                int currentCount =
                        countByCoach.getOrDefault(coach, 0);

                countByCoach.put(
                        coach,
                        currentCount + 1
                );
            }
        }

        List<CounterOfTrainings> result = new ArrayList<>();

        for (Map.Entry<Coach, Integer> entry : countByCoach.entrySet()) {
            result.add(
                    new CounterOfTrainings(
                            entry.getKey(),
                            entry.getValue()
                    )
            );
        }

        result.sort(CounterOfTrainings.BY_COUNT_DESCENDING);

        return result;
    }
}