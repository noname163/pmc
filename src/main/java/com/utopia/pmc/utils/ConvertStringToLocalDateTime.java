package com.utopia.pmc.utils;

import java.nio.Buffer;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class ConvertStringToLocalDateTime {
    public LocalTime convertStringToLocalTime(String format, String time) {
        if (time == null || time.equals("")) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalTime.parse(time, formatter);
    }

    public LocalDate convertStringToLocalDate(String date) {
        String year = date.substring(0, 4);
        String month = date.substring(4, 6);
        String day = date.substring(6, 8);
        return LocalDate.of(
                Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(day));
    }
}
