package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Controller
public class MealRestController {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private MealService service;

    public Meal create(Meal meal){
        log.info("create {}", meal);
        return service.create(meal, AuthorizedUser.id());
    }

    public void delete(int id){
        service.delete(id, AuthorizedUser.id());
    }

    public void update(Meal meal){
        service.update(meal, AuthorizedUser.id());
    }

    public Meal get(int id){
        log.info("get {}", service.get(id, AuthorizedUser.id()));
        return service.get(id, AuthorizedUser.id());
    }

    public List<MealWithExceed> getAll(){
        log.info("getAll");
        List<Meal> list = service.getAll(AuthorizedUser.id());
        return MealsUtil.getWithExceeded(list, MealsUtil.DEFAULT_CALORIES_PER_DAY);
    }

    public List<MealWithExceed> getFilteredList(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime){
            return service.getFilteredList(startDate, startTime, endDate, endTime, AuthorizedUser.id());
    }
}