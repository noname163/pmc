package com.utopia.pmc.utils;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

import org.springframework.stereotype.Component;

@Component
public class ConvertStringToLocalTime {
    public LocalTime convertStringToLocalTime(String format, String time) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(format);
        return LocalTime.parse(time, formatter);
    }
}
