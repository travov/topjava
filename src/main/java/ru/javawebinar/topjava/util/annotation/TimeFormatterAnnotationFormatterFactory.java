package ru.javawebinar.topjava.util.annotation;

import org.springframework.format.AnnotationFormatterFactory;
import org.springframework.format.Parser;
import org.springframework.format.Printer;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class TimeFormatterAnnotationFormatterFactory implements AnnotationFormatterFactory<TimeFormatter> {
    @Override
    public Set<Class<?>> getFieldTypes() {
        return new HashSet<Class<?>>(Collections.singletonList(LocalTime.class));
    }

    @Override
    public Printer<?> getPrinter(TimeFormatter annotation, Class<?> fieldType) {
        return new TimeParser();
    }

    @Override
    public Parser<?> getParser(TimeFormatter annotation, Class<?> fieldType) {
        return new TimeParser();
    }
}
