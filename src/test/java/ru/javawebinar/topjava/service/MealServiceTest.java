package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.MealTestData.MEALS_LIST;
import static ru.javawebinar.topjava.MealTestData.MEAL_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
public class MealServiceTest {

    @Autowired
    private MealService service;

    @Test
    public void get() throws Exception {
        Meal actual = service.get(MEAL_ID, AuthorizedUser.id());
        assertThat(actual).isEqualToIgnoringGivenFields(MEALS_LIST.get(0), "id");
    }

    @Test(expected = NotFoundException.class)
    public void getNotFound() throws Exception {
        service.get(100007, AuthorizedUser.id());
    }

    @Test
    public void delete() throws Exception {
        service.delete(MEAL_ID, AuthorizedUser.id());
        List<Meal> expected = new ArrayList<>(MEALS_LIST);
        expected.remove(0);
        expected.remove(4);
        expected = expected.stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
        assertThat(service.getAll(AuthorizedUser.id())).usingElementComparatorIgnoringFields("id").isEqualTo(expected);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() throws Exception {
        service.delete(1, AuthorizedUser.id());
    }



    @Test
    public void getBetweenDates() throws Exception {
        List<Meal> expected = new ArrayList<>(MEALS_LIST);
        expected.remove(5);
        assertThat(service.getBetweenDates(LocalDate.MIN, LocalDate.MAX, AuthorizedUser.id())).usingElementComparatorIgnoringFields("id")
                .isEqualTo(expected);

    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> expected = new ArrayList<>(MEALS_LIST);
        expected.remove(5);
        assertThat(service.getBetweenDateTimes(LocalDateTime.MIN, LocalDateTime.MAX, AuthorizedUser.id())).usingElementComparatorIgnoringFields("id")
                .isEqualTo(expected);
    }

    @Test
    public void getAll() throws Exception {
        List<Meal> expected = new ArrayList<>(MEALS_LIST).stream().sorted(Comparator.comparing(Meal::getDateTime).reversed()).collect(Collectors.toList());
        expected.remove(0);
        assertThat(service.getAll(AuthorizedUser.id())).usingElementComparatorIgnoringFields("id").isEqualTo(expected);
    }

    @Test
    public void update() throws Exception {
        Meal expected = service.get(MEAL_ID, AuthorizedUser.id());
        expected.setCalories(1000);
        expected.setDateTime(LocalDateTime.of(2015, Month.MAY, 30, 10, 0));
        service.update(expected, AuthorizedUser.id());
        assertThat(service.get(MEAL_ID, AuthorizedUser.id())).isEqualTo(expected);

    }

    @Test(expected = NotFoundException.class)
    public void updateNotFound() throws Exception{
        Meal expected = service.get(1, AuthorizedUser.id());
        expected.setCalories(1000);
        expected.setDateTime(LocalDateTime.of(2015, Month.MAY, 30, 10, 0));
        service.update(expected, AuthorizedUser.id());
    }

    @Test
    public void create() throws Exception {
        Meal newMeal = new Meal(null, LocalDateTime.now(), "Ужин", 3000);
        Meal created = service.create(newMeal, AuthorizedUser.id());
        newMeal.setId(created.getId());
        assertThat(created).isEqualTo(newMeal);
    }

}