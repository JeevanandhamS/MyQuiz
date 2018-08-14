package com.jeeva.myquiz.data.db;

import android.arch.persistence.room.TypeConverter;
import android.text.TextUtils;

import java.util.regex.Pattern;

/**
 * Created by Jeevanandham on 13/08/2018
 */
public class StringArrayTypeConverter {

    private static final String SEPARATOR = "|";

    @TypeConverter
    public static String[] toStringArray(String value) {
        if(TextUtils.isEmpty(value)) {
            return null;
        }

        return value.split(Pattern.quote(SEPARATOR));
    }

    @TypeConverter
    public static String fromStringArray(String[] values) {
        if(null == values || values.length == 0) {
            return null;
        }

        return TextUtils.join(SEPARATOR, values);
    }
}