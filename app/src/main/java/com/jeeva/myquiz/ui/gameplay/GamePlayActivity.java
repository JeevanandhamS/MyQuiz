package com.jeeva.myquiz.ui.gameplay;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.util.Log;

import com.jeeva.myquiz.R;
import com.jeeva.myquiz.data.dto.Question;
import com.jeeva.myquiz.databinding.ActivityGamePlayBinding;
import com.jeeva.myquiz.ui.base.BaseActivity;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

/**
 * Created by Jeevanandham on 13/08/2018
 * <p>
 * {@link GamePlayActivity} class which provides user interface for playing the game
 * to answer the questions with set of options
 * </p>
 */
public class GamePlayActivity extends BaseActivity {

    private static final String TAG = GamePlayActivity.class.getName();

    @Inject
    GamePlayViewModel mGamePlayViewModel;

    private List<Question> mQuestionList = new ArrayList<>();

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
                    this.mQuestionList = questions;
                    Log.d(TAG, "Random Questions --> " + questions.toString());
                });
    }
}