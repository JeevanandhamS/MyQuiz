package com.jeeva.myquiz.data.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;

import com.jeeva.myquiz.data.dao.UserDao;
import com.jeeva.myquiz.data.dto.User;

/**
 * Created by Jeevanandham on 12/08/2018
 */
@Database(entities = {User.class}, version = 1, exportSchema = false)
@TypeConverters(DateTypeConverter.class)
public abstract class MyQuizDatabase extends RoomDatabase {

    public abstract UserDao getUserDao();
}