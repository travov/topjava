package ru.javawebinar.topjava.util;

import ru.javawebinar.topjava.model.UserMeal;
import ru.javawebinar.topjava.model.UserMealWithExceed;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.*;
import java.util.stream.Collectors;

/**
 * GKislin
 * 31.05.2015.
 */
public class UserMealsUtil {
    private static String[] description = {"Завтрак", "Обед", "Ужин"};
    public static void main(String[] args) {
        List<UserMeal> mealList = new ArrayList<>();
        for (int i = 0;i < 1000000;i++){
            int year = (int) ((Math.random() * (2020 - 2010)) + 2010);
            int day = (int) ((Math.random() * (30 - 1)) + 1);
            int hour = (int) ((Math.random() * (23 - 1)) + 1);
            String descript = description[(int) ((Math.random() * (3 - 1)) + 1)];
            int calories = (int) ((Math.random() * (3000 - 1)) + 1);
            mealList.add(new UserMeal(LocalDateTime.of(year, Month.MAY, day, hour, 0), descript, calories));
        }
        Date before = new Date();
        getFilteredWithExceeded(mealList, LocalTime.of(7, 0), LocalTime.of(12, 0), 2000);
        Date after = new Date();
        System.out.println(after.getTime() - before.getTime());

    }

    public static List<UserMealWithExceed>  getFilteredWithExceeded(List<UserMeal> mealList, LocalTime startTime, LocalTime endTime, int caloriesPerDay) {
        // TODO return filtered list with correctly exceeded field
        Map<LocalDate, Integer> caloriesSumByDate = mealList.stream().collect(Collectors.groupingBy(um -> um.getDateTime().toLocalDate(), Collectors.summingInt(UserMeal::getCalories)));
        return mealList.stream().filter(um -> TimeUtil.isBetween(um.getDateTime().toLocalTime(), startTime, endTime))
                .map(um -> new UserMealWithExceed(um.getDateTime(), um.getDescription(), um.getCalories(),
                        caloriesSumByDate.get(um.getDateTime().toLocalDate()) > caloriesPerDay)).collect(Collectors.toList());
    }
}
