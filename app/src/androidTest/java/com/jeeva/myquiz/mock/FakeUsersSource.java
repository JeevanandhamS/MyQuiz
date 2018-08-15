package com.jeeva.myquiz.mock;

import com.jeeva.myquiz.data.dto.User;

import org.threeten.bp.OffsetDateTime;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class FakeUsersSource {

    private static FakeUsersSource INSTANCE;

    private static final Map<String, User> USERS_SERVICE_DATA = new LinkedHashMap<>();

    private static String FAKE_USER_NAME_1 = "FAKE NAME 1";

    private static int FAKE_USER_AGE_1 = 23;

    private static String FAKE_USER_GENDER_1 = "Male";

    private FakeUsersSource() {}

    public static FakeUsersSource getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new FakeUsersSource();
        }
        return INSTANCE;
    }

    public static User fetchFakeUser() {
        return new User(FAKE_USER_NAME_1, FAKE_USER_AGE_1, FAKE_USER_GENDER_1, OffsetDateTime.now());
    }

    public static List<User> getFakeUsers(int size, boolean withPoints) {
        List<User> userList = new ArrayList<User>();
        for(int i = 1; i <= size; i++ ) {
            User user = new User("Name " + i, 23 + i, i % 2 == 0 ? "Male" : "Female",
                    OffsetDateTime.now());
            if(withPoints) {
                user.setPoints(i * 10);
            }
            userList.add(user);
        }
        return userList;
    }
}