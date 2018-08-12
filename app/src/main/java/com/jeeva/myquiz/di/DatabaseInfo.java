package com.jeeva.myquiz.di;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Qualifier;

/**
 * Created by Jeevanandham on 12/08/2018
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
public @interface DatabaseInfo {
}