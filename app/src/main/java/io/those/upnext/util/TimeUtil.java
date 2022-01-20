package io.those.upnext.util;

import android.util.Log;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Optional;

public class TimeUtil {
    public static final ZoneId UTC = ZoneId.of("UTC");

    public static Optional<Long> toMillis(LocalDate date, ZoneId zoneId) {
        return toMillis(date.atStartOfDay(), zoneId);
    }

    public static Optional<Long> toMillis(LocalDateTime dateTime, ZoneId zoneId) {
        try {
            ZonedDateTime zdt = ZonedDateTime.of(dateTime, zoneId);
            return Optional.of(zdt.toInstant().toEpochMilli());
        } catch (Exception e) {
            Log.e(TimeUtil.class.getSimpleName(), "toMillis failed.", e);
        }

        return Optional.empty();
    }

    public static Optional<LocalDate> toDate(long millis, ZoneId zoneId) {
        try {
            return toDateTime(millis, zoneId).map(LocalDateTime::toLocalDate);
        } catch (Exception e) {
            Log.e(TimeUtil.class.getSimpleName(), "toDate failed.", e);
        }

        return Optional.empty();
    }

    public static Optional<LocalDateTime> toDateTime(long millis, ZoneId zoneId) {
        try {
            return Optional.of(LocalDateTime.ofInstant(Instant.ofEpochMilli(millis), zoneId));
        } catch (Exception e) {
            Log.e(TimeUtil.class.getSimpleName(), "toDateTime failed.", e);
        }

        return Optional.empty();
    }
}
