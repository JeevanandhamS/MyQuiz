package com.jeeva.myquiz;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.jakewharton.threetenabp.AndroidThreeTen;
import com.jeeva.myquiz.di.DaggerAppComponent;

import javax.inject.Inject;

import dagger.android.AndroidInjection;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;
import uk.co.chrisjenx.calligraphy.CalligraphyConfig;

/**
 * Created by Jeevanandham on 12/08/2018
 */
public class MyQuizApplication extends Application implements HasActivityInjector {

    @Inject
    DispatchingAndroidInjector<Activity> mDispatchingAndroidInjector;

    @Inject
    CalligraphyConfig mCalligraphyConfig;

    @Override
    public void onCreate() {
        super.onCreate();

        AndroidThreeTen.init(this);

        DaggerAppComponent.builder()
                .application(this)
                .build()
                .inject(this);

        registerActivityLifecycleCallbacks(new ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(Activity activity, Bundle bundle) {
                AndroidInjection.inject(activity);
            }

            @Override
            public void onActivityStarted(Activity activity) {
            }

            @Override
            public void onActivityResumed(Activity activity) {
            }

            @Override
            public void onActivityPaused(Activity activity) {
            }

            @Override
            public void onActivityStopped(Activity activity) {
            }

            @Override
            public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {
            }

            @Override
            public void onActivityDestroyed(Activity activity) {
            }
        });

        CalligraphyConfig.initDefault(mCalligraphyConfig);
    }

    @Override
    public AndroidInjector<Activity> activityInjector() {
        return mDispatchingAndroidInjector;
    }
}