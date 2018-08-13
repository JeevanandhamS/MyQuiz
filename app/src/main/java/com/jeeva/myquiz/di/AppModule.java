package com.jeeva.myquiz.di;

import android.arch.persistence.room.Room;
import android.content.Context;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jeeva.myquiz.AppConstants;
import com.jeeva.myquiz.MyQuizApplication;
import com.jeeva.myquiz.data.dao.QuestionDao;
import com.jeeva.myquiz.data.dao.UserDao;
import com.jeeva.myquiz.data.db.MyQuizDatabase;
import com.jeeva.myquiz.ui.gameplay.PointsManager;

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
    PointsManager providePointsManager() {
        return new PointsManager();
    }

    @Provides
    @Singleton
    QuestionDao provideQuestionDao(MyQuizDatabase myQuizDatabase) {
        return myQuizDatabase.getQuestionDao();
    }

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

    @Provides
    @Singleton
    Gson provideGson() {
        return new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
    }
}
