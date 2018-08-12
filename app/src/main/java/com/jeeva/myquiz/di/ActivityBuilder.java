package com.jeeva.myquiz.di;

import com.jeeva.myquiz.ui.launcher.LauncherActivity;
import com.jeeva.myquiz.ui.launcher.LauncherModule;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;

/**
 * Created by Jeevanandham on 12/08/2018
 */
@Module
public abstract class ActivityBuilder {

    @ContributesAndroidInjector(modules = LauncherModule.class)
    abstract LauncherActivity bindLauncherActivity();
}