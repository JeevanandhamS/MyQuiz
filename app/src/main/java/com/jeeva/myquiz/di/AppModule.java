package com.jeeva.myquiz.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.jeeva.myquiz.AppConstants;
import com.jeeva.myquiz.MyQuizApplication;
import com.jeeva.myquiz.data.dao.UserDao;
import com.jeeva.myquiz.data.db.MyQuizDatabase;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Jeevanandham on 12/08/2018
 */
@Module
public class AppModule {

    @Provides
    @Singleton
    UserDao provideUserDao(MyQuizDatabase myQuizDatabase) {
        return myQuizDatabase.getUserDao();
    }

    @Provides
    @Singleton
    MyQuizDatabase provideMyQuizDatabase(@DatabaseInfo String dbName, Context context) {
        return Room.databaseBuilder(context, MyQuizDatabase.class, dbName)
                .build();
    }

    @Provides
    @Singleton
    CalligraphyConfig provideCalligraphyDefaultConfig() {
        return new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/Product_Sans_Regular.ttf")
                .build();
    }

    @Provides
    @Singleton
    Context provideContext(MyQuizApplication application) {
        return application;
    }

    @Provides
    @DatabaseInfo
    String provideDatabaseName() {
        return AppConstants.DB_NAME;
    }
}
