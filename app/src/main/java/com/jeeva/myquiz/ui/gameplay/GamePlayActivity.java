package com.jeeva.myquiz.ui.gameplay;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.jeeva.myquiz.R;
import com.jeeva.myquiz.databinding.ActivityGamePlayBinding;
import com.jeeva.myquiz.ui.base.BaseActivity;
import com.jeeva.myquiz.ui.launcher.LauncherActivity;

import java.util.Stack;

import javax.inject.Inject;

/**
 * Created by Jeevanandham on 13/08/2018
 * <p>
 * {@link GamePlayActivity} class which provides user interface for playing the game
 * to answer the questions with set of options
 * </p>
 */
public class GamePlayActivity extends BaseActivity implements GamePlayFragment.OnGamePlayListener {

    private static final String TAG = GamePlayActivity.class.getName();

    @Inject
    GamePlayViewModel mGamePlayViewModel;

    private Stack<GamePlayQuestion> mQuestionStack = new Stack<>();

    private ActivityGamePlayBinding mGamePlayBinding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.mGamePlayBinding = DataBindingUtil.setContentView(this, R.layout.activity_game_play);

        initViews();

        fetchQuestions();
    }

    private void initViews() {

    }

    private void fetchQuestions() {
        mGamePlayViewModel.getRandomQuestions()
                .subscribe(questions -> {
                    Log.d(TAG, "Random Questions --> " + questions.toString());

                    mQuestionStack.clear();

                    for (int i = questions.size() - 1; i >= 0; i--) {
                        mQuestionStack.push(new GamePlayQuestion(questions.get(i), i + 1));
                    }

                    loadNextQuestion();
                });
    }

    private void loadNextQuestion() {
        if(mQuestionStack.empty()) {
            goToLauncher();
            return;
        }

        GamePlayQuestion playQuestion = mQuestionStack.pop();
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.game_play_fl_frame, GamePlayFragment.newInstance(playQuestion))
                .commitAllowingStateLoss();
    }

    private void goToLauncher() {
        Intent intent = new Intent(this, LauncherActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    public void onQuestionAnswered(int timeLeft, boolean answerRight) {
        mGamePlayViewModel.onQuestionAnswered(timeLeft, answerRight);
        loadNextQuestion();
    }

    @Override
    public void onTimeOut() {
        mGamePlayViewModel.onQuestionTimeOut();
        loadNextQuestion();
    }
}