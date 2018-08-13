package com.jeeva.myquiz.ui.launcher;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;
import android.text.TextUtils;

import com.jeeva.myquiz.data.dao.QuestionDao;
import com.jeeva.myquiz.data.dao.UserDao;
import com.jeeva.myquiz.data.dto.Question;
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
    QuestionDao mQuestionDao;

    @Inject
    UserDao mUserDao;

    @Inject
    OnUserInfoModelListener mUserInfoModelListener;

    @Inject
    public LauncherViewModel(QuestionDao questionDao, UserDao userDao,
                             OnUserInfoModelListener userInfoModelListener) {
        this.mQuestionDao = questionDao;
        this.mUserDao = userDao;
        this.mUserInfoModelListener = userInfoModelListener;
    }

    public void updateQuestionAvailability() {
        Observable
                .fromCallable(() -> mQuestionDao.getNoOfQuestions())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        noOfQuestions -> mUserInfoModelListener.onGetNoOfQuestions(noOfQuestions)
                );
    }

    public void addQuestions(List<Question> questions) {
        Observable
                .just(questions)
                .doOnNext(questions1 -> mQuestionDao.addQuestions(questions1))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }

    public LiveData<List<User>> getTopPerformers() {
        return mUserDao.getTopPerformers(TOP_PERFORMER_LIMIT);
    }

    public void addNewUser(final User user) {
        if (!validateUserInfo(user)) {
            return;
        }

        user.setCreatedTime(OffsetDateTime.now());

        Observable
                .just(user)
                .doOnNext(user1 -> user1.setId(mUserDao.addUser(user1)))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        user1 -> mUserInfoModelListener.onUserCreatedSuccess(user1),
                        throwable -> mUserInfoModelListener.onUserNameNotUnique()
                );
    }

    private boolean validateUserInfo(User user) {
        if (TextUtils.isEmpty(user.getName())) {
            mUserInfoModelListener.onUserNameEmpty();
            return false;
        }

        if (user.getName().length() < 4) {
            mUserInfoModelListener.onUserNameSmall();
            return false;
        }

        if (user.getAge() < 7) {
            mUserInfoModelListener.onAgeSmall();
            return false;
        }

        if (TextUtils.isEmpty(user.getGender())) {
            mUserInfoModelListener.onGenderEmpty();
            return false;
        }

        return true;
    }

    public interface OnUserInfoModelListener {

        void onGetNoOfQuestions(int noOfQuestions);

        void onUserNameEmpty();

        void onUserNameSmall();

        void onAgeSmall();

        void onGenderEmpty();

        void onUserNameNotUnique();

        void onUserCreatedSuccess(User user);
    }
}