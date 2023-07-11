package com.flz.nettystudy.common.utils;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public final class DateUtils {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd hh:mm:ss");

    public static String getCurrentTimeStr() {
        return FORMATTER.format(LocalDateTime.now());
    }
}
