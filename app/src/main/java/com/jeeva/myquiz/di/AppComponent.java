package com.jeeva.myquiz.di;

import com.jeeva.myquiz.MyQuizApplication;

import javax.inject.Singleton;

import dagger.BindsInstance;
import dagger.Component;
import dagger.android.AndroidInjectionModule;

/**
 * Created by Jeevanandham on 12/08/2018
 */
@Singleton
@Component(modules = {AndroidInjectionModule.class, AppModule.class, ActivityBuilder.class})
public interface AppComponent {

    void inject(MyQuizApplication application);

    @Component.Builder
    interface Builder {

        @BindsInstance
        Builder application(MyQuizApplication application);

        AppComponent build();
    }
}