package com.jeeva.myquiz.ui.gameplay;

import com.jeeva.myquiz.AppConstants;
import com.jeeva.myquiz.data.dao.QuestionDao;
import com.jeeva.myquiz.data.dao.UserDao;
import com.jeeva.myquiz.data.dto.User;

import org.parceler.Parcels;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Jeevanandham on 13/08/2018
 */
@Module
public class GamePlayModule {

    @Provides
    GamePlayViewModel provideGamePlayViewModel(User user, QuestionDao questionDao,
                                               UserDao userDao, PointsManager pointsManager) {
        return new GamePlayViewModel(user, questionDao, userDao, pointsManager);
    }

    @Provides
    User provideUser(GamePlayActivity playActivity) {
        return Parcels.unwrap(playActivity.getIntent().getExtras()
                .getParcelable(AppConstants.USER_DATA_KEY));
    }
}