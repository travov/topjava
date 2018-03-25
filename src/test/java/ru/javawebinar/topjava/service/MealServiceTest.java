package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.AfterClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.rules.TestWatcher;
import org.junit.runner.Description;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))
@Transactional
public class MealServiceTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private static Map<String, Long> durations = new HashMap<>();

    @Rule
    public TestWatcher watcher = new TestWatcher() {
        LocalTime start;
        @Override
        protected void starting(Description description) {
            start = LocalTime.now();
        }

        @Override
        protected void finished(Description description) {
            long execution = Duration.between(start, LocalTime.now()).toMillis();
            durations.put(description.getMethodName(), execution);
            java.util.logging.Logger.getLogger("ru.javawebinar.topjava").info(description.getMethodName() + "method execution time is " + execution);
        }
    };

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void delete() throws Exception {
        service.delete(MEAL1_ID, USER_ID);
        //assertMatch(service.getAll(USER_ID), MEAL6, MEAL5, MEAL4, MEAL3, MEAL2);
        assertWithoutUser(service.getAll(USER_ID), Arrays.asList(MEAL6, MEAL5, MEAL4, MEAL3, MEAL2));
    }

    @Test
    public void deleteNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.delete(MEAL1_ID, 1);
    }

    @Test
    public void save() throws Exception {
        Meal created = getCreated();
        service.create(created, USER_ID);
        //assertMatch(service.getAll(USER_ID), created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1);
       assertWithoutUser(service.getAll(USER_ID), Arrays.asList(created, MEAL6, MEAL5, MEAL4, MEAL3, MEAL2, MEAL1));
    }

    @Test
    public void get() throws Exception {
        Meal actual = service.get(ADMIN_MEAL_ID, ADMIN_ID);
        //assertMatch(actual, ADMIN_MEAL1);
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(ADMIN_MEAL1, "user");
    }

    @Test
    public void getNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.get(MEAL1_ID, ADMIN_ID);
    }

    @Test
    public void update() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
       //assertMatch(service.get(MEAL1_ID, USER_ID), updated);
        Assertions.assertThat(service.get(MEAL1_ID, USER_ID)).isEqualToIgnoringGivenFields(updated, "user");
    }

    @Test
    public void updateNotFound() throws Exception {
        thrown.expect(NotFoundException.class);
        service.update(MEAL1, ADMIN_ID);
    }

    @Test
    public void getAll() throws Exception {
        //assertMatch(service.getAll(USER_ID), MEALS);
        assertWithoutUser(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void getBetween() throws Exception {
        /*assertMatch(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), MEAL3, MEAL2, MEAL1);*/
        assertWithoutUser(service.getBetweenDates(
                LocalDate.of(2015, Month.MAY, 30),
                LocalDate.of(2015, Month.MAY, 30), USER_ID), Arrays.asList(MEAL3, MEAL2, MEAL1));
    }

    @AfterClass
    public static void printSummary(){
        durations.forEach((k, v) -> System.out.println(k + " time of execution is " + v));
    }
}