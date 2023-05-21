package com.utopia.pmc.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class ConvertStringToLocalTime {
    public LocalTime convertStringToLocalTime(String format, String time) {
        if (time == null || time.equals("")) {
            return null;
        }
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalTime.parse(time, formatter);
    }
}
