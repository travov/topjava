package ru.javawebinar.topjava.service;

import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.Profiles;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.User;

@ActiveProfiles(Profiles.DATAJPA)
public class DataJpaUserServiceTest extends UserServiceTest {

    @Test
    public void getWithMeals() throws Exception{
        User actual = service.getWithMeals(UserTestData.ADMIN_ID);
        User expected = UserTestData.ADMIN;
        Assertions.assertThat(actual).isEqualToIgnoringGivenFields(expected, "registered", "roles", "meals");
        Assertions.assertThat(actual.getMeals()).isEqualTo(expected.getMeals());

    }
}
