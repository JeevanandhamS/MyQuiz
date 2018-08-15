package com.jeeva.myquiz.data.dto;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

import org.parceler.Parcel;
import org.threeten.bp.OffsetDateTime;

import static com.jeeva.myquiz.data.dto.User.FIELD_NAME;
import static com.jeeva.myquiz.data.dto.User.TABLE_NAME;

/**
 * Created by Jeevanandham on 12/08/2018
 */
@Parcel
@Entity(tableName = TABLE_NAME, indices = {@Index(value = FIELD_NAME, unique = true)})
public class User {

    public static final String TABLE_NAME = "USER";

    public static final String FIELD_ID = "ID";

    public static final String FIELD_NAME = "NAME";

    public static final String FIELD_AGE = "AGE";

    public static final String FIELD_GENDER = "GENDER";

    public static final String FIELD_POINTS = "POINTS";

    public static final String FIELD_CREATED_TIME = "CREATED_TIME";

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = FIELD_ID)
    private long id;

    @ColumnInfo(name = FIELD_NAME)
    private String name;

    @ColumnInfo(name = FIELD_AGE)
    private int age;

    @ColumnInfo(name = FIELD_GENDER)
    private String gender;

    @ColumnInfo(name = FIELD_POINTS)
    private int points = 0;

    @ColumnInfo(name = FIELD_CREATED_TIME)
    private OffsetDateTime createdTime;

    public User() {
    }

    public User(String name, int age, String gender, OffsetDateTime createdTime) {
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.createdTime = createdTime;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public OffsetDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(OffsetDateTime createdTime) {
        this.createdTime = createdTime;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", gender='" + gender + '\'' +
                '}';
    }
}