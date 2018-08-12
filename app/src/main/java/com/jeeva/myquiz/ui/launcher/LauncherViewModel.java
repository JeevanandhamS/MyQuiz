package com.jeeva.myquiz.ui.launcher;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.jeeva.myquiz.data.dao.UserDao;
import com.jeeva.myquiz.data.dto.User;

import org.threeten.bp.OffsetDateTime;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jeevanandham on 12/08/2018
 */
public class LauncherViewModel extends ViewModel {

    private static final int TOP_PERFORMER_LIMIT = 5;

    @Inject
    UserDao mUserDao;

    @Inject
    OnUserInfoModelListener mUserInfoModelListener;

    @Inject
    public LauncherViewModel(UserDao userDao, OnUserInfoModelListener userInfoModelListener) {
        this.mUserDao = userDao;
        this.mUserInfoModelListener = userInfoModelListener;
    }

    public LiveData<List<User>> getTopPerformers() {
        return mUserDao.getTopPerformers(TOP_PERFORMER_LIMIT);
    }

    public void addNewUser(final User user) {
        if(!validateUserInfo(user)) {
            return;
        }

        user.setCreatedTime(OffsetDateTime.now());

        Observable
                .just(user)
                .map(user1 -> mUserDao.addUser(user1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        userId -> mUserInfoModelListener.onUserCreatedSuccess(userId),
                        throwable -> mUserInfoModelListener.onUserNameNotUnique()
                );
    }

    private boolean validateUserInfo(User user) {
        if(TextUtils.isEmpty(user.getName())) {
            mUserInfoModelListener.onUserNameEmpty();
            return false;
        }

        if(user.getName().length() < 4) {
            mUserInfoModelListener.onUserNameSmall();
            return false;
        }

        if(user.getAge() < 7) {
            mUserInfoModelListener.onAgeSmall();
            return false;
        }

        if(TextUtils.isEmpty(user.getGender())) {
            mUserInfoModelListener.onGenderEmpty();
            return false;
        }

        return true;
    }

    public interface OnUserInfoModelListener {

        void onUserNameEmpty();

        void onUserNameSmall();

        void onAgeSmall();

        void onGenderEmpty();

        void onUserNameNotUnique();

        void onUserCreatedSuccess(long userId);
    }
}