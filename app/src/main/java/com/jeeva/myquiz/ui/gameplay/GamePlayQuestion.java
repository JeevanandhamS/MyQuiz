package com.jeeva.myquiz.ui.gameplay;

import com.jeeva.myquiz.data.dto.Question;

import org.parceler.Parcel;

/**
 * Created by Jeevanandham on 14/08/2018
 */
@Parcel
public class GamePlayQuestion {

    private Question questionData;

    private int questionNo;

    public GamePlayQuestion() {
    }

    public GamePlayQuestion(Question questionData, int questionNo) {
        this.questionData = questionData;
        this.questionNo = questionNo;
    }

    public Question getQuestionData() {
        return questionData;
    }

    public void setQuestionData(Question questionData) {
        this.questionData = questionData;
    }

    public int getQuestionNo() {
        return questionNo;
    }

    public void setQuestionNo(int questionNo) {
        this.questionNo = questionNo;
    }
}