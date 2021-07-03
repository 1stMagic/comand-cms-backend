package com.biock.cms.utils;

import javax.validation.constraints.NotNull;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.Calendar;
import java.util.Date;

public final class DateUtils {

    private DateUtils() {

        // Empty
    }

    public static OffsetDateTime toOffsetDateTime(@NotNull final Calendar date, @NotNull final ZoneOffset timeZoneOffset) {

        return date.toInstant().atOffset(timeZoneOffset);
    }

    public static Calendar toCalendar(@NotNull final OffsetDateTime date) {

        return new Calendar.Builder()
                .setInstant(Date.from(date.toInstant()))
                .build();
    }
}
