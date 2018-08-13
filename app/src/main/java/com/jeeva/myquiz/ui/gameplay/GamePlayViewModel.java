package com.jeeva.myquiz.ui.gameplay;

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

    public Observable<List<Question>> getRandomQuestions() {
        return Observable
                .fromCallable(() -> mQuestionDao.getRandomQuestions(QUESTION_LIMIT))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public void onQuestionAnswered(Question question, int userAnswer, int timeLeft) {
        if(userAnswer == question.getAnswer()) {
            updateUserPoints(mPointsManager.calcCorrectAnswerPoints(timeLeft));
        } else {
            updateUserPoints(mPointsManager.calcWrongAnswerPoints());
        }
    }

    public void onQuestionNotAnswered(Question question) {
        updateUserPoints(mPointsManager.calcWrongAnswerPoints());
    }

    private void updateUserPoints(int points) {
        int newPoints = mUser.getPoints() + points;
        mUser.setPoints(newPoints);
        Completable.fromAction(() -> mUserDao.updateUser(mUser));
    }
}