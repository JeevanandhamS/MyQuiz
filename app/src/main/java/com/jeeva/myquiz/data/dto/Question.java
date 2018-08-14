package com.jeeva.myquiz.data.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import org.parceler.Parcel;

import static com.jeeva.myquiz.data.dto.Question.TABLE_NAME;

/**
 * Created by Jeevanandham on 13/08/2018
 */
@Entity(tableName = TABLE_NAME)
@Parcel
public class Question {

    public static final String TABLE_NAME = "QUESTION";

    public static final String FIELD_ID = "ID";

    public static final String FIELD_QUESTION = "QUESTION";

    public static final String FIELD_OPTIONS = "OPTIONS";

    public static final String FIELD_ANSWER = "ANSWER";

    public Question() {
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FIELD_ID)
    private long id;

    @ColumnInfo(name = FIELD_QUESTION)
    @Expose
    @SerializedName("question")
    private String question;

    @ColumnInfo(name = FIELD_OPTIONS)
    @Expose
    @SerializedName("options")
    private String[] options;

    @ColumnInfo(name = FIELD_ANSWER)
    @Expose
    @SerializedName("answer")
    private int answer;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String[] getOptions() {
        return options;
    }

    public void setOptions(String[] options) {
        this.options = options;
    }

    public int getAnswer() {
        return answer;
    }

    public void setAnswer(int answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", question='" + question + '\'' +
                ", options=" + options +
                ", answer=" + answer +
                '}';
    }
}