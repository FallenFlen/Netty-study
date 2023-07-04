package com.flz.nettystudy.common.utils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class DateUtils {
    private DateUtils() {

    }

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public static String getCurrentTimeStr() {
        return FORMATTER.format(LocalDateTime.now());
    }
}
