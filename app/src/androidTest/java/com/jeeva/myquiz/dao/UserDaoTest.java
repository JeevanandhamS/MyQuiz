package com.jeeva.myquiz.dao;

import android.arch.core.executor.testing.InstantTaskExecutorRule;
import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.jeeva.myquiz.LiveDataTestUtil;
import com.jeeva.myquiz.data.dao.UserDao;
import com.jeeva.myquiz.data.db.MyQuizDatabase;
import com.jeeva.myquiz.data.dto.User;
import com.jeeva.myquiz.mock.FakeUsersSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Jeevanandham on 15/08/2018
 */
@RunWith(AndroidJUnit4.class)
public class UserDaoTest {

    @Rule
    public InstantTaskExecutorRule instantTaskExecutorRule = new InstantTaskExecutorRule();

    private MyQuizDatabase mMyQuizDatabase;

    private UserDao mUserDao;

    @Before
    public void initDb() throws Exception {
        mMyQuizDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getContext(),
                MyQuizDatabase.class)
                .allowMainThreadQueries()
                .build();

        mUserDao = mMyQuizDatabase.getUserDao();
    }

    @After
    public void closeDb() throws Exception {
        mMyQuizDatabase.close();
    }

    @Test
    public void onFetchingUsers_shouldGetEmptyList_IfTable_IsEmpty() throws InterruptedException {
        List<User> userList = LiveDataTestUtil.getValue(mUserDao.getTopPerformers(5));
        assertTrue(userList.isEmpty());
    }

    @Test
    public void onInsertingUsers_shouldGetEmptyList_IfAllUsers_HasNoPoints() throws InterruptedException {
        List<User> userList = FakeUsersSource.getFakeUsers(5, false);
        userList.forEach(user -> {
            mUserDao.addUser(user);
        });
        assertTrue(LiveDataTestUtil.getValue(mUserDao.getTopPerformers(5)).isEmpty());
    }

    @Test
    public void onInsertingUsers_checkIf_TopPerformers_RowCountIsCorrect() throws InterruptedException {
        List<User> userList = FakeUsersSource.getFakeUsers(5, true);
        userList.forEach(user -> {
            mUserDao.addUser(user);
        });
        assertEquals(5, LiveDataTestUtil.getValue(mUserDao.getTopPerformers(5)).size());
    }
}