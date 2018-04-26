package ru.javawebinar.topjava.util.annotation;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import java.time.LocalDate;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;


public class DateFormatterAnnotationFormatterFactory implements AnnotationFormatterFactory<DateFormatter> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(Collections.singletonList(LocalDate.class));
    }

    @Override
    public Printer<?> getPrinter(DateFormatter annotation, Class<?> fieldType) {
        return new DateParser();
    }

    @Override
    public Parser<?> getParser(DateFormatter annotation, Class<?> fieldType) {
        return new DateParser();
    }

}
