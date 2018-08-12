package com.jeeva.myquiz.data.db;

import android.arch.persistence.room.TypeConverter;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

/**
 * Created by Jeevanandham on 12/08/2018
 */
public class DateTypeConverter {

    @TypeConverter
    public static OffsetDateTime toOffsetDateTime(String value) {
        return null != value ? OffsetDateTime.parse(value) : null;
    }

    @TypeConverter
    public static String fromOffsetDataTime(OffsetDateTime date) {
        return null != date ? date.format(DateTimeFormatter.ISO_OFFSET_DATE_TIME) : null;
    }
}