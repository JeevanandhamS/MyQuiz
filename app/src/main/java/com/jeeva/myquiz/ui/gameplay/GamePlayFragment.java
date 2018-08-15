package com.jeeva.myquiz.ui.gameplay;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.button.MaterialButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.LinearInterpolator;

import com.jeeva.myquiz.AppConstants;
import com.jeeva.myquiz.R;
import com.jeeva.myquiz.data.dto.Question;
import com.jeeva.myquiz.databinding.FragmentGamePlayBinding;

import org.parceler.Parcels;

/**
 * Created by Jeevanandham on 14/08/2018
 */
public class GamePlayFragment extends Fragment implements View.OnClickListener {

    public static GamePlayFragment newInstance(GamePlayQuestion playQuestion) {
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.QUESTION_DATA_KEY, Parcels.wrap(playQuestion));

        GamePlayFragment fragment = new GamePlayFragment();
        fragment.setArguments(bundle);
        return fragment;
    }

    public interface OnGamePlayListener {

        void onQuestionAnswered(int timeLeft, boolean answerRight);

        void onTimeOut();
    }

    private OnGamePlayListener mGamePlayListener;

    private FragmentGamePlayBinding mGamePlayBinding;

    private GamePlayQuestion mGamePlayQuestion;

    private MaterialButton mBtnRightOption;

    private ObjectAnimator mTimerAnimator;

    private boolean mAnswerRight = false;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnGamePlayListener) {
            this.mGamePlayListener = (OnGamePlayListener) context;
        } else {
            throw new IllegalArgumentException("OnGamePlayListener should be implemented by parent activity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        mGamePlayBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game_play,
                container, false);

        unBundleQuestion();
        populateQuestionAndOptions(mGamePlayQuestion);
        startTimer();

        return mGamePlayBinding.getRoot();
    }

    private void unBundleQuestion() {
        mGamePlayQuestion = Parcels.unwrap(getArguments().getParcelable(AppConstants.QUESTION_DATA_KEY));
    }

    private void populateQuestionAndOptions(GamePlayQuestion playQuestion) {
        Question questionData = playQuestion.getQuestionData();
        mGamePlayBinding.gamePlayTvQuestion.setText(playQuestion.getQuestionNo()
                + ". " + questionData.getQuestion());

        String[] options = questionData.getOptions();
        int answerPos = questionData.getAnswer();

        setOption(mGamePlayBinding.gamePlayBtnOption1, options, 1, answerPos);
        setOption(mGamePlayBinding.gamePlayBtnOption2, options, 2, answerPos);
        setOption(mGamePlayBinding.gamePlayBtnOption3, options, 3, answerPos);
        setOption(mGamePlayBinding.gamePlayBtnOption4, options, 4, answerPos);
    }

    private void setOption(MaterialButton btnOption, String[] options, int option, int answer) {
        btnOption.setText(options[option - 1]);
        btnOption.setOnClickListener(this);

        if(answer == option) {
            mBtnRightOption = btnOption;
        }
    }

    private void startTimer() {
        mGamePlayBinding.gamePlayPbTimer.setMax(100);
        mGamePlayBinding.gamePlayPbTimer.setProgress(100);

        mTimerAnimator = ObjectAnimator.ofInt(mGamePlayBinding.gamePlayPbTimer,
                "progress", 100, 0);
        mTimerAnimator.setDuration(10000L);
        mTimerAnimator.setInterpolator(new LinearInterpolator());
        mTimerAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {
            }

            @Override
            public void onAnimationEnd(Animator animator) {
                onTimerStopped();
            }

            @Override
            public void onAnimationCancel(Animator animator) {
            }

            @Override
            public void onAnimationRepeat(Animator animator) {
            }
        });
        mTimerAnimator.start();
    }

    private void stopTimer() {
        if(mTimerAnimator.isRunning()) {
            mTimerAnimator.cancel();
        }
    }

    private void onTimerStopped() {
        mGamePlayBinding.gamePlayViewClickDisable.setVisibility(View.VISIBLE);

        mGamePlayBinding.gamePlayPbTimer.postDelayed(() -> {
            int timeLeft = mGamePlayBinding.gamePlayPbTimer.getProgress();
            if(timeLeft > 0) {
                mGamePlayListener.onQuestionAnswered(timeLeft / 10, mAnswerRight);
            } else {
                mGamePlayListener.onTimeOut();
            }
        }, 1000L);
    }

    @Override
    public void onClick(View view) {
        stopTimer();

        if (view == mBtnRightOption) {
            mAnswerRight = true;
        } else {
            ((MaterialButton) view).setBackgroundTintList(ColorStateList.valueOf(Color.RED));
        }

        mBtnRightOption.setBackgroundTintList(ColorStateList.valueOf(Color.GREEN));
    }
}