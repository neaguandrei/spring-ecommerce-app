package aneagu.proj.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateFormatterUtils {
    private static final String PATTERN = "dd-MM-yyyy";

    public static String serialize(Date date) {
        return serialize(date, PATTERN);
    }

    public static String serialize(Date date, String pattern) {
        Objects.requireNonNull(pattern, "pattern argument should not be null");
        if (Objects.isNull(date)) {
            return null;
        }
        final DateFormat format = new SimpleDateFormat(pattern, Locale.GERMANY);
        return format.format(date);
    }
}
