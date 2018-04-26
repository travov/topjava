package ru.javawebinar.topjava.web.meal;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.AbstractControllerTest;
import ru.javawebinar.topjava.web.json.JsonUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.web.meal.MealRestController.REST_URL;

public class MealRestControllerTest extends AbstractControllerTest {

    @Test
    public void getAll() throws Exception {
        mockMvc.perform(get(REST_URL)).
                andDo(print())
                .andExpect(status().isOk())
                .andExpect(contentJson(MealsUtil.getWithExceeded(MealTestData.MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY)));
    }

    @Test
    public void getMeal() throws Exception {
        mockMvc.perform(get(REST_URL + "/" + MEAL5.getId())).
                andDo(print())
                .andExpect(status().isOk())
                .andExpect(contentJson(MealTestData.MEAL5));
    }

    @Test
    public void createWithLocation() throws Exception {
        Meal created = new Meal(MealTestData.created);
        mockMvc.perform(MockMvcRequestBuilders.post(REST_URL).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(created)))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(contentJson(created));
        created.setId(MEAL1_ID + 8);
        assertMatch(mealService.get(created.getId(), AuthorizedUser.id()), created);
    }

    @Test
    public void delete() throws Exception {
        List<MealWithExceed> list = MealsUtil.getWithExceeded(MealTestData.MEALS, MealsUtil.DEFAULT_CALORIES_PER_DAY);
        list.remove(4);
        mockMvc.perform(MockMvcRequestBuilders.delete(REST_URL + "/" + MEAL5.getId()))
                .andDo(print())
                .andExpect(status().isNoContent());
        List<Meal> actual = mealService.getAll(AuthorizedUser.id());
        List<Meal> expected = new ArrayList<>(MealTestData.MEALS);
        expected.remove(MEAL5);
        assertMatch(actual, expected);
    }

    @Test
    public void update() throws Exception {
        Meal updated = new Meal(MealTestData.updated);
        mockMvc.perform(MockMvcRequestBuilders.put(REST_URL + "/" + MEAL5.getId()).contentType(MediaType.APPLICATION_JSON).content(JsonUtil.writeValue(updated)))
                .andDo(print())
                .andExpect(status().isOk());
        assertMatch(mealService.get(updated.getId(), AuthorizedUser.id()), updated);
    }

    @Test
    public void getBetween() throws Exception {
        mockMvc.perform(get(REST_URL + "/filter")
        .param("startDate", "2015-05-01")
        .param("startTime", "07:00")
        .param("endDate", "2015-05-30")
        .param("endTime", "20:00"))
                .andDo(print())
                .andExpect(status().isOk())
        .andExpect(contentJson(Arrays.asList(MEAL1, MEAL2, MEAL3)));
    }

}