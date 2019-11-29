package com.huijiewei.agile.core.until;

import org.apache.commons.lang3.StringUtils;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateTimeUtils {
    public static LocalDateTime[] parseSearchDateRange(String[] searchDateRange) {
        if (searchDateRange == null) {
            return null;
        }

        if (searchDateRange.length != 2) {
            return null;
        }

        if (StringUtils.isEmpty(searchDateRange[0]) || StringUtils.isEmpty(searchDateRange[1])) {
            return null;
        }


        try {
            LocalDateTime startDate = LocalDateTime.parse(searchDateRange[0] + " 00:00:00", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
            LocalDateTime endDate = LocalDateTime.parse(searchDateRange[1] + " 23:59:59", DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

            return new LocalDateTime[]{startDate, endDate};
        } catch (DateTimeParseException ex) {
            return null;
        }

    }
}
