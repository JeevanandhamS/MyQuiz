package com.jeeva.myquiz.ui.gameplay;

/**
 * Created by Jeevanandham on 14/08/2018
 */
public class PointsManager {

    private static final int CORRECT_ANSWER_POINTS = 20;

    private static final int WRONG_ANSWER_POINTS = -10;

    public int calcCorrectAnswerPoints(int timeLeft) {
        return CORRECT_ANSWER_POINTS + timeLeft;
    }

    public int calcWrongAnswerPoints() {
        return WRONG_ANSWER_POINTS;
    }
}