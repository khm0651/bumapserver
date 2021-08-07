package com.example.demo;

import java.time.DayOfWeek;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class UTC {
    public static final ZoneId GMT9_ZONE = ZoneId.of("GMT+9");
    public static final ZoneId UTC_ZONE = ZoneId.of("UTC");
    public static final String ISO_FIXED_DATE_TIME_PATTERN = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    public static final DateTimeFormatter ISO_FIXED_DATE_TIME = DateTimeFormatter.ofPattern(ISO_FIXED_DATE_TIME_PATTERN).withZone(UTC_ZONE);
    public static final String MYSQL_DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss.SSS";
    public static final DateTimeFormatter MYSQL_DATE_TIME = DateTimeFormatter.ofPattern(MYSQL_DATE_TIME_PATTERN).withZone(UTC_ZONE);
    public static final String MYSQL_DATE_PATTERN = "yyyy-MM-dd";
    public static final DateTimeFormatter MYSQL_DATE = DateTimeFormatter.ofPattern(MYSQL_DATE_PATTERN).withZone(UTC_ZONE);
    public static final DateTimeFormatter MYSQL_DATE_GMT9 = DateTimeFormatter.ofPattern(MYSQL_DATE_PATTERN).withZone(GMT9_ZONE);
    public static final String YEAR_TO_DAY = "yyyyMMdd";
    public static final DateTimeFormatter YEAR_TO_DAY_PATTERN = DateTimeFormatter.ofPattern(YEAR_TO_DAY).withZone(UTC_ZONE);
    public static final String YEAR_TO_MILLISECONDS = "yyyyMMddHHmmssSSS";
    public static final DateTimeFormatter YEAR_TO_MILLISECONDS_PATTERN = DateTimeFormatter.ofPattern(YEAR_TO_MILLISECONDS).withZone(UTC_ZONE);

    public static ZonedDateTime now() {
        return ZonedDateTime.now(UTC_ZONE);
    }

    public static ZonedDateTime parseOrNull(String text) {
        try {
            return ZonedDateTime.parse(text, UTC.ISO_FIXED_DATE_TIME);

        } catch(Exception e) {
            return null;
        }
    }

    public static ZonedDateTime getGmt9_0000(ZonedDateTime utc) {
        return utc.withZoneSameInstant(GMT9_ZONE).truncatedTo(ChronoUnit.DAYS);
    }

    public static DayOfWeek getGmt9_DayOfWeek(ZonedDateTime utc) {
        return utc.withZoneSameInstant(GMT9_ZONE).getDayOfWeek();
    }

    public static String getIsoFixedDateTimeStringFrom(ZonedDateTime datetime) {
        return datetime.format(ISO_FIXED_DATE_TIME);
    }

    public static String getMySqlDateTimeStringFrom(ZonedDateTime datetime) {
        return datetime.format(MYSQL_DATE_TIME);
    }

    public static String getMySqlDateStringFrom(ZonedDateTime datetime) {
        return datetime.format(MYSQL_DATE);
    }

    public static String getMySqlDateStringInGmt9From(ZonedDateTime datetime) {
        return datetime.format(MYSQL_DATE_GMT9);
    }

    public static String getYearToDay(ZonedDateTime datetime) {
        return datetime.format(YEAR_TO_DAY_PATTERN);
    }

    public static String getYearToMilliseconds(ZonedDateTime datetime) {
        return datetime.format(YEAR_TO_MILLISECONDS_PATTERN);
    }
}