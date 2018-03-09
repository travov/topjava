package ru.javawebinar.topjava.dao;

import ru.javawebinar.topjava.model.Meal;

import java.util.concurrent.ConcurrentMap;

public interface MealDao {

    void addMeal(Meal meal);
    void deleteMeal(int id);
    void update(Meal meal);
    ConcurrentMap<Integer, Meal> getAllMeals();
    Object getMealById(int id);
}
