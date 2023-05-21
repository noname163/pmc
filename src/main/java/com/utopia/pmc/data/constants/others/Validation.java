package com.utopia.pmc.data.constants.others;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class Validation {
    public static final String DATE_FORMAT = "dd/MM/yyyy";
    public static final String TIME_FORMAT = "HH:ss";
    public static final String PHONE_REGEX = "^\\d{10}$";
    public static final String ONLY_ALPHABET_REGEX = "^.*[A-Za-z].*$";
    public static final String EMAIL_REGEX = "^[A-Za-z0-9._]+@gmail\\.[A-Za-z]{2,3}$";
    public static final String ONLY_ALPHABET_AND_SPACE_REGEX = "^.*[A-Za-z].*[\\s]?+.*$";
    public static final String ONLY_ALPHABET_AND_NUMBER_REGEX = "^[a-zA-Z0-9]*$";
    public static final String ONLY_ALPHABET_AND_NUMBER_AND_SPACE_REGEX = "^[a-zA-Z0-9 ]*$";
    public static final int MIN_LENGTH_PASSWORD = 6;
    public static final int MIN_AGE = 16;
    public static final int MAX_AGE = 100;
    public static final int MAX_LENGTH_PASSWORD = 24;
    public static final int MAX_LENGTH_INPUT = 100;
    public static final int MAX_LENGTH_TEXTAREA = 500;
}
