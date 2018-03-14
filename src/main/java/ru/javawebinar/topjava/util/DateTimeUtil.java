package ru.javawebinar.topjava.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtil {
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    public static boolean isBetween(LocalTime lt, LocalTime startTime, LocalTime endTime) {
        return isBetweenDateOrTime(lt, startTime, endTime);
    }

    public static <T extends Comparable<T>> boolean isBetweenDateOrTime(T lt, T startTime, T endTime){
        return lt.compareTo(startTime) >= 0 && lt.compareTo(endTime) <= 0;
    }

    public static LocalDate tryParseDate(String value){
        try {
            return LocalDate.parse(value);
        }
        catch (DateTimeParseException | NullPointerException e){
            return null;
        }
    }

    public static LocalTime tryParseTime(String value){
        try {
            return LocalTime.parse(value);
        }
        catch (DateTimeParseException | NullPointerException e){
            return null;
        }
    }

    public static String toString(LocalDateTime ldt) {
        return ldt == null ? "" : ldt.format(DATE_TIME_FORMATTER);
    }
}
