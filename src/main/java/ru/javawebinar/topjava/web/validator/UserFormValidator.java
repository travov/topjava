package ru.javawebinar.topjava.web.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.service.UserService;
import ru.javawebinar.topjava.to.UserTo;
import ru.javawebinar.topjava.util.UserUtil;
import ru.javawebinar.topjava.util.exception.NotFoundException;

@Component
public class UserFormValidator implements Validator {

    @Autowired
    private UserService service;

    @Override
    public boolean supports(Class<?> clazz) {
        return UserTo.class.equals(clazz);
    }

    @Override
    public void validate(@Nullable Object target, Errors errors) {
        UserTo userTo = (UserTo) target;
        User user = UserUtil.createNewFromTo(userTo);
           try {
              User userWithEmail = service.getByEmail(user.getEmail());
           if (user.getEmail() != null && userWithEmail != null){
               errors.rejectValue("email", "user.valid.email");
              }
           }
           catch (NotFoundException e){

           }
    }
}
