package com.jeeva.myquiz.ui.launcher;

import com.jeeva.myquiz.data.dao.QuestionDao;
import com.jeeva.myquiz.data.dao.UserDao;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jeevanandham on 12/08/2018
 */
@Module
public class LauncherModule {

    @Provides
    LauncherViewModel provideLauncherViewModel(QuestionDao questionDao, UserDao userDao,
                                               LauncherViewModel.OnUserInfoModelListener userInfoModelListener) {
        return new LauncherViewModel(questionDao, userDao, userInfoModelListener);
    }

    @Provides
    LauncherViewModel.OnUserInfoModelListener provideUserInfoModelListener(LauncherActivity activity) {
        return activity;
    }
}