package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class MealDaoImpl implements MealDao {

    private int id = 1;
    private static ConcurrentHashMap<Integer, Meal> map = new ConcurrentHashMap<>();

    public MealDaoImpl() {
        map.put(id++, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500));
        map.put(id++, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000));
        map.put(id++, new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500));
        map.put(id++, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000));
        map.put(id++, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500));
        map.put(id++, new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));
        map.forEach((k, v) -> v.setId(k));
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setId(id++);
        map.put(meal.getId(), meal);
    }

    @Override
    public void deleteMeal(int id) {
       Meal meal = map.remove(id);
        System.out.println(meal.getDescription());
    }

    @Override
    public void update(Meal meal) {
        int id = meal.getId();
        Meal old = getMealById(id);
        map.replace(meal.getId(), old, meal);
        System.out.println(getMealById(id).getCalories());
    }

    @Override
    public ConcurrentMap<Integer, Meal> getAllMeals() {
        return map;
    }

    @Override
    public Meal getMealById(int id) {
        return map.entrySet().stream().filter(entry -> entry.getKey() == id).map(Map.Entry::getValue).findFirst().get();

    }
}
