package com.jeeva.myquiz.data.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;

import com.jeeva.myquiz.data.dto.Question;

import java.util.List;

/**
 * Created by Jeevanandham on 13/08/2018
 */
@Dao
public interface QuestionDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void addQuestions(List<Question> questions);

    @Query("SELECT * FROM " + Question.TABLE_NAME + " ORDER BY RANDOM() LIMIT " + ":questionLimit")
    List<Question> getRandomQuestions(int questionLimit);

    @Query("SELECT COUNT (*) FROM " + Question.TABLE_NAME)
    int getNoOfQuestions();
}