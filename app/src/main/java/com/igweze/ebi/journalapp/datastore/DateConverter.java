package com.igweze.ebi.journalapp.datastore;

import java.util.Date;

class DateConverter {
    public static Date toDate(Long timestamp) {
        return timestamp == null ? null : new Date(timestamp);
    }

    public static Long toTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }
}
