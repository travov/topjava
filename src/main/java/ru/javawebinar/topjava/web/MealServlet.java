package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.javawebinar.topjava.dao.MealDao;
import ru.javawebinar.topjava.dao.MealDaoImpl;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealServlet extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(MealServlet.class);


    private MealDao dao;
    public MealServlet() {
        super();
        dao = new MealDaoImpl();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String forward = "";
        String action = req.getParameter("action");
        if (action == null){
            forward = "/meals.jsp";
            req.setAttribute("listOfAllMeals", MealsUtil.getFilteredWithExceeded(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000).stream().
                    sorted(Comparator.comparingInt(MealWithExceed::getId)).collect(Collectors.toList()));
        }
        else if (action.equalsIgnoreCase("edit")){
            forward = "/mealForm.jsp";
            req.setAttribute("meal", dao.getMealById(Integer.parseInt(req.getParameter("id"))));
        }
        else if (action.equalsIgnoreCase("delete")){
            dao.deleteMeal(Integer.parseInt(req.getParameter("id")));
            forward = "/meals.jsp";
            req.setAttribute("listOfAllMeals", MealsUtil.getFilteredWithExceeded(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000).stream().
                    sorted(Comparator.comparingInt(MealWithExceed::getId)).collect(Collectors.toList()));
        }
        else if (action.equalsIgnoreCase("insert")){
            forward = "/mealForm.jsp";
        }
        req.getRequestDispatcher(forward).forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String date = req.getParameter("date");


        if (req.getParameter("id").equals("")){
            Meal meal = new Meal(LocalDateTime.of(Integer.valueOf(date.split(" ")[0]), Integer.valueOf(date.split(" ")[1]),
                    Integer.valueOf(date.split(" ")[2]),
                    Integer.valueOf(date.split(" ")[3].split(":")[0]), Integer.valueOf(date.split(" ")[3].split(":")[1])),
                    req.getParameter("description"), Integer.valueOf(req.getParameter("calories")));
            dao.addMeal(meal);
        }
        else {
            Meal meal = new Meal(LocalDateTime.of(Integer.valueOf(date.split("-")[0]), Integer.valueOf(date.split("-")[1]),
                    Integer.valueOf(date.split("-")[2].split("T")[0]),
                    Integer.valueOf(date.split("T")[1].split(":")[0]), Integer.valueOf(date.split("T")[1].split(":")[1])),
                    req.getParameter("description"), Integer.valueOf(req.getParameter("calories")));
            meal.setId(Integer.parseInt(req.getParameter("id")));
            dao.update(meal);
        }
        req.setAttribute("listOfAllMeals", MealsUtil.getFilteredWithExceeded(dao.getAllMeals(), LocalTime.MIN, LocalTime.MAX, 2000).stream().
                sorted(Comparator.comparingInt(MealWithExceed::getId)).collect(Collectors.toList()));
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }
}
