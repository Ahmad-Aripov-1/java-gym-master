package ru.yandex.practicum.gym;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.*;

public class TimetableTest {

    @Test
    void testGetTrainingSessionsForDaySingleSession() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        List<TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        Assertions.assertEquals(1, mondaySessions.size());
        Assertions.assertEquals(0, tuesdaySessions.size());
    }

    @Test
    void testGetTrainingSessionsForDayMultipleSessions() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");

        Group groupAdult = new Group("Акробатика для взрослых", Age.ADULT, 90);
        TrainingSession thursdayAdultTrainingSession = new TrainingSession(groupAdult, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(20, 0));

        timetable.addNewTrainingSession(thursdayAdultTrainingSession);

        Group groupChild = new Group("Акробатика для детей", Age.CHILD, 60);
        TrainingSession mondayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));
        TrainingSession thursdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.THURSDAY, new TimeOfDay(13, 0));
        TrainingSession saturdayChildTrainingSession = new TrainingSession(groupChild, coach,
                DayOfWeek.SATURDAY, new TimeOfDay(10, 0));

        timetable.addNewTrainingSession(mondayChildTrainingSession);
        timetable.addNewTrainingSession(thursdayChildTrainingSession);
        timetable.addNewTrainingSession(saturdayChildTrainingSession);

        List<TrainingSession> mondaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.MONDAY);

        List<TrainingSession> thursdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.THURSDAY);

        List<TrainingSession> tuesdaySessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.TUESDAY);

        Assertions.assertEquals(1, mondaySessions.size());

        Assertions.assertEquals(2, thursdaySessions.size());

        Assertions.assertEquals(
                13,
                thursdaySessions.get(0).getTimeOfDay().getHours()
        );

        Assertions.assertEquals(
                20,
                thursdaySessions.get(1).getTimeOfDay().getHours()
        );

        Assertions.assertEquals(0, tuesdaySessions.size());
    }

    @Test
    void testGetTrainingSessionsForDayAndTime() {
        Timetable timetable = new Timetable();

        Group group = new Group("Акробатика для детей", Age.CHILD, 60);
        Coach coach = new Coach("Васильев", "Николай", "Сергеевич");
        TrainingSession singleTrainingSession = new TrainingSession(group, coach,
                DayOfWeek.MONDAY, new TimeOfDay(13, 0));

        timetable.addNewTrainingSession(singleTrainingSession);

        List<TrainingSession> sessionsAt13 =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY,
                        new TimeOfDay(13, 0)
                );

        List<TrainingSession> sessionsAt14 =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY,
                        new TimeOfDay(14, 0)
                );

        Assertions.assertEquals(1, sessionsAt13.size());
        Assertions.assertEquals(0, sessionsAt14.size());
    }

    @Test
    void testGetMultipleTrainingSessionsAtSameTime() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");

        Group group1 = new Group("Акробатика", Age.CHILD, 60);
        Group group2 = new Group("Гимнастика", Age.CHILD, 60);

        TrainingSession session1 = new TrainingSession(
                group1,
                coach1,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        TrainingSession session2 = new TrainingSession(
                group2,
                coach2,
                DayOfWeek.MONDAY,
                new TimeOfDay(13, 0)
        );

        timetable.addNewTrainingSession(session1);
        timetable.addNewTrainingSession(session2);

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDayAndTime(
                        DayOfWeek.MONDAY,
                        new TimeOfDay(13, 0)
                );

        Assertions.assertEquals(2, sessions.size());
    }

    @Test
    void testTrainingSessionsAreSortedByTime() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Смирнов", "Алексей", "Олегович");
        Group group = new Group("Гимнастика", Age.ADULT, 60);

        TrainingSession sessionEvening = new TrainingSession(
                group,
                coach,
                DayOfWeek.WEDNESDAY,
                new TimeOfDay(20, 0)
        );

        TrainingSession sessionMorning = new TrainingSession(
                group,
                coach,
                DayOfWeek.WEDNESDAY,
                new TimeOfDay(10, 0)
        );

        TrainingSession sessionAfternoon = new TrainingSession(
                group,
                coach,
                DayOfWeek.WEDNESDAY,
                new TimeOfDay(15, 0)
        );

        timetable.addNewTrainingSession(sessionEvening);
        timetable.addNewTrainingSession(sessionMorning);
        timetable.addNewTrainingSession(sessionAfternoon);

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.WEDNESDAY);

        Assertions.assertEquals(3, sessions.size());

        Assertions.assertEquals(
                10,
                sessions.get(0).getTimeOfDay().getHours()
        );

        Assertions.assertEquals(
                15,
                sessions.get(1).getTimeOfDay().getHours()
        );

        Assertions.assertEquals(
                20,
                sessions.get(2).getTimeOfDay().getHours()
        );
    }

    @Test
    void testTrainingSessionsAreSortedByMinutes() {
        Timetable timetable = new Timetable();

        Coach coach = new Coach("Иванов", "Иван", "Иванович");
        Group group = new Group("Гимнастика", Age.ADULT, 60);

        TrainingSession session45 = new TrainingSession(
                group,
                coach,
                DayOfWeek.FRIDAY,
                new TimeOfDay(13, 45)
        );

        TrainingSession session00 = new TrainingSession(
                group,
                coach,
                DayOfWeek.FRIDAY,
                new TimeOfDay(13, 0)
        );

        TrainingSession session30 = new TrainingSession(
                group,
                coach,
                DayOfWeek.FRIDAY,
                new TimeOfDay(13, 30)
        );

        timetable.addNewTrainingSession(session45);
        timetable.addNewTrainingSession(session00);
        timetable.addNewTrainingSession(session30);

        List<TrainingSession> sessions =
                timetable.getTrainingSessionsForDay(DayOfWeek.FRIDAY);

        Assertions.assertEquals(3, sessions.size());

        Assertions.assertEquals(
                0,
                sessions.get(0).getTimeOfDay().getMinutes()
        );

        Assertions.assertEquals(
                30,
                sessions.get(1).getTimeOfDay().getMinutes()
        );

        Assertions.assertEquals(
                45,
                sessions.get(2).getTimeOfDay().getMinutes()
        );
    }

    @Test
    void testGetCountByCoaches() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach("Иванов", "Иван", "Иванович");
        Coach coach2 = new Coach("Петров", "Пётр", "Петрович");

        Group group = new Group("Акробатика", Age.ADULT, 60);

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach1,
                        DayOfWeek.MONDAY,
                        new TimeOfDay(10, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach1,
                        DayOfWeek.WEDNESDAY,
                        new TimeOfDay(10, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach1,
                        DayOfWeek.FRIDAY,
                        new TimeOfDay(10, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach2,
                        DayOfWeek.TUESDAY,
                        new TimeOfDay(15, 0)
                )
        );

        List<CounterOfTrainings> result =
                timetable.getCountByCoaches();

        Assertions.assertEquals(2, result.size());

        Assertions.assertEquals(coach1, result.get(0).getCoach());
        Assertions.assertEquals(3, result.get(0).getCount());

        Assertions.assertEquals(coach2, result.get(1).getCoach());
        Assertions.assertEquals(1, result.get(1).getCount());
    }

    @Test
    void testSameCoachObjectsAreCountedTogether() {
        Timetable timetable = new Timetable();

        Coach coach1 = new Coach(
                "Иванов",
                "Иван",
                "Иванович"
        );

        Coach coach2 = new Coach(
                "Иванов",
                "Иван",
                "Иванович"
        );

        Group group = new Group(
                "Акробатика",
                Age.ADULT,
                60
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach1,
                        DayOfWeek.MONDAY,
                        new TimeOfDay(10, 0)
                )
        );

        timetable.addNewTrainingSession(
                new TrainingSession(
                        group,
                        coach2,
                        DayOfWeek.WEDNESDAY,
                        new TimeOfDay(15, 0)
                )
        );

        List<CounterOfTrainings> result =
                timetable.getCountByCoaches();

        Assertions.assertEquals(1, result.size());
        Assertions.assertEquals(2, result.get(0).getCount());
    }

    @Test
    void testGetCountByCoachesForEmptyTimetable() {
        Timetable timetable = new Timetable();

        List<CounterOfTrainings> result =
                timetable.getCountByCoaches();

        Assertions.assertTrue(result.isEmpty());
    }

}
