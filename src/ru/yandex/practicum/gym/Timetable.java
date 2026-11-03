package ru.yandex.practicum.gym;

import java.util.*;

public class Timetable {

    private Map<DayOfWeek, List<TrainingSession>> timetable = new HashMap<>();

    public void addNewTrainingSession(TrainingSession trainingSession) {

        DayOfWeek dayOfWeek = trainingSession.getDayOfWeek();

        if (!timetable.containsKey(dayOfWeek)) {
            timetable.put(dayOfWeek, new ArrayList<>());
        }

        timetable.get(dayOfWeek).add(trainingSession);

        timetable.get(dayOfWeek).sort(
                Comparator.comparing(TrainingSession::getTimeOfDay)
        );
    }

    public List<TrainingSession> getTrainingSessionsForDay(DayOfWeek dayOfWeek) {
        return timetable.getOrDefault(dayOfWeek, Collections.emptyList());
    }

    public List<TrainingSession> getTrainingSessionsForDayAndTime(
            DayOfWeek dayOfWeek,
            TimeOfDay timeOfDay
    ) {
        List<TrainingSession> sessions = timetable.get(dayOfWeek);

        if (sessions == null) {
            return Collections.emptyList();
        }

        int left = 0;
        int right = sessions.size() - 1;

        while (left <= right) {
            int middle = (left + right) / 2;

            TrainingSession session = sessions.get(middle);

            int comparison = session.getTimeOfDay().compareTo(timeOfDay);

            if (comparison < 0) {
                left = middle + 1;
            } else if (comparison > 0) {
                right = middle - 1;
            } else {
                // нашли тренировку с нужным временем

                List<TrainingSession> result = new ArrayList<>();

                // идём влево от найденной тренировки
                int index = middle;

                while (index >= 0 &&
                        sessions.get(index).getTimeOfDay().equals(timeOfDay)) {

                    result.add(sessions.get(index));
                    index--;
                }

                // переворачиваем, потому что шли справа налево
                Collections.reverse(result);

                // идём вправо
                index = middle + 1;

                while (index < sessions.size() &&
                        sessions.get(index).getTimeOfDay().equals(timeOfDay)) {

                    result.add(sessions.get(index));
                    index++;
                }

                return result;
            }
        }

        return Collections.emptyList();
    }

    public List<CounterOfTrainings> getCountByCoaches() {

        Map<Coach, Integer> countByCoach = new HashMap<>();

        for (List<TrainingSession> sessions : timetable.values()) {
            for (TrainingSession session : sessions) {

                Coach coach = session.getCoach();

                int currentCount = countByCoach.getOrDefault(coach, 0);

                countByCoach.put(coach, currentCount + 1);
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

        result.sort(
                (counter1, counter2) ->
                        Integer.compare(
                                counter2.getCount(),
                                counter1.getCount()
                        )
        );

        return result;
    }
}