package ru.javawebinar.topjava.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.util.ValidationUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class MealServiceImpl implements MealService {

    private MealRepository repository;

    @Autowired
    public MealServiceImpl(MealRepository repository) {
        this.repository = repository;
    }

    @Override
    public Meal create(Meal meal, int userId) {
        return repository.save(meal, userId);
    }

    @Override
    public void delete(int id, int userId) throws NotFoundException {
        ValidationUtil.checkNotFoundWithId(repository.delete(id, userId), id);
    }

    @Override
    public Meal get(int id, int userId) throws NotFoundException {
        return ValidationUtil.checkNotFoundWithId(repository.get(id, userId), id);
    }

    @Override
    public void update(Meal meal, int userId) {
        ValidationUtil.checkNotFoundWithId(repository.save(meal, userId), meal.getId());
    }

    @Override
    public List<Meal> getAll() {
        List<Meal> list = (List<Meal>) repository.getAll();
        if (list != null)
           return list;
        else return Collections.<Meal>emptyList();
    }

    @Override
    public List<MealWithExceed> getFilteredList(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        List<MealWithExceed> list = MealsUtil.getWithExceeded(repository.getFilteredList(startDate, endDate), 2000);
        LocalTime start = startTime == null? LocalTime.MIN: startTime;
        LocalTime end = endTime == null? LocalTime.MAX: endTime;
        list = list.stream().filter(meal -> DateTimeUtil.isBetweenDateOrTime(meal.getDateTime().toLocalTime(), start, end)).collect(Collectors.toList());

        if (list != null)
            return list;
        else return Collections.<MealWithExceed>emptyList();
    }
}