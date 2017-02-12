package com.peterservice.logging;

import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.LogRecord;

/**
 * Значения даты и времени для записей в логгер
 *
 */

public class RecordFormatter extends Formatter {

    private static final String format = "%1$tH:%1$tM:%1$tS %2$s: %3$s%n";

    private final Date date = new Date();

    @Override
    public String format(LogRecord record) {
        date.setTime(record.getMillis());
        return String.format(format,
                date, record.getLevel(), record.getMessage());
    }
}
