package com.gamersclubfinder.gamersclubfinder.util;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class DateUtil {

    public static String secondsEpochToDate(Long secondsEpoch) {
        Instant instant = Instant.ofEpochSecond(secondsEpoch);
        LocalDateTime localDateTime = LocalDateTime.ofInstant(instant, ZoneId.of("UTC"));

        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return localDateTime.format(fmt);
    }

    public static String minutesToHours(Long minutes) {
        long hours = minutes / 60;

        return Long.toString(hours);
    }
}