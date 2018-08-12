package com.jeeva.myquiz.data.dao;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.jeeva.myquiz.data.dto.User;

import java.util.List;

/**
 * Created by Jeevanandham on 12/08/2018
 */
@Dao
public interface UserDao {

    @Insert(onConflict = OnConflictStrategy.FAIL)
    long addUser(User user);

    @Update(onConflict = OnConflictStrategy.FAIL)
    void updateUser(User user);

    @Query("SELECT * FROM " + User.TABLE_NAME + " ORDER BY " + User.FIELD_POINTS + " DESC Limit " + ":userLimit")
    LiveData<List<User>> getTopPerformers(int userLimit);
}