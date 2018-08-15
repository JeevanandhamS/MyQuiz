package com.jeeva.myquiz.ui.gameplay;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.jeeva.myquiz.data.dao.QuestionDao;
import com.jeeva.myquiz.data.dao.UserDao;
import com.jeeva.myquiz.data.dto.Question;
import com.jeeva.myquiz.data.dto.User;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Completable;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Jeevanandham on 13/08/2018
 */
public class GamePlayViewModel extends ViewModel {

    private static final int QUESTION_LIMIT = 5;

    @Inject
    User mUser;

    @Inject
    QuestionDao mQuestionDao;

    @Inject
    UserDao mUserDao;

    @Inject
    PointsManager mPointsManager;

    @Inject
    public GamePlayViewModel(User user, QuestionDao questionDao,
                             UserDao userDao, PointsManager pointsManager) {
        this.mUser = user;
        this.mQuestionDao = questionDao;
        this.mUserDao = userDao;
        this.mPointsManager = pointsManager;
    }

    public LiveData<Integer> getLiveUserPoints() {
        return mUserDao.getUserPoints(mUser.getId());
    }

    public User getUserInfo() {
        return mUser;
    }

    public Observable<List<Question>> getRandomQuestions() {
        return Observable
                .fromCallable(() -> mQuestionDao.getRandomQuestions(QUESTION_LIMIT))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void onQuestionAnswered(int timeLeft, boolean answerRight) {
        if (answerRight) {
            updateUserPoints(mPointsManager.calcCorrectAnswerPoints(timeLeft));
        } else {
            updateUserPoints(mPointsManager.calcWrongAnswerPoints());
        }
    }

    public void onQuestionTimeOut() {
        updateUserPoints(mPointsManager.calcWrongAnswerPoints());
    }

    private void updateUserPoints(int points) {
        mUser.setPoints(mUser.getPoints() + points);

        Completable
                .fromAction(() -> mUserDao.updateUser(mUser))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe();
    }
}