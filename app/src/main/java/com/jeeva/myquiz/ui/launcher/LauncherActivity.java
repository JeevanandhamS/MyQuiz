package com.jeeva.myquiz.ui.launcher;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;

import com.google.gson.Gson;
import com.jeeva.myquiz.AppConstants;
import com.jeeva.myquiz.R;
import com.jeeva.myquiz.data.dto.Question;
import com.jeeva.myquiz.data.dto.QuestionList;
import com.jeeva.myquiz.data.dto.User;
import com.jeeva.myquiz.databinding.ActivityLauncherBinding;
import com.jeeva.myquiz.ui.base.BaseActivity;
import com.jeeva.myquiz.ui.gameplay.GamePlayActivity;
import com.jeeva.myquiz.utils.AppUtils;

import org.parceler.Parcels;

import java.util.List;

import javax.inject.Inject;

/**
 * Created by Jeevanandham on 12/08/2018
 * <p>
 * {@link LauncherActivity} class which provides user interface for getting user info to enroll
 * and to show the top performers
 * </p>
 */
public class LauncherActivity extends BaseActivity implements LauncherViewModel.OnUserInfoModelListener {

    private static final String TAG = LauncherActivity.class.getName();

    @Inject
    Gson mGson;

    @Inject
    LauncherViewModel mLauncherViewModel;

    private ActivityLauncherBinding mLauncherBinding;

    private TopPerformerAdapter mTopPerformerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mLauncherViewModel.updateQuestionAvailability();

        this.mLauncherBinding = DataBindingUtil.setContentView(this, R.layout.activity_launcher);

        initViews();

        fetchTopPerformers();
    }

    private void initViews() {
        mTopPerformerAdapter = new TopPerformerAdapter(getLayoutInflater());

        mLauncherBinding.userInfoRcvPerformers.setLayoutManager(
                new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mLauncherBinding.userInfoRcvPerformers.setHasFixedSize(true);
        mLauncherBinding.userInfoRcvPerformers.setAdapter(mTopPerformerAdapter);

        mLauncherBinding.userInfoBtnPlay.setOnClickListener(
                view -> {
                    dismissKeyboard();

                    mLauncherBinding.userInfoIlName.setErrorEnabled(false);
                    mLauncherBinding.userInfoIlAge.setErrorEnabled(false);

                    User user = new User();
                    user.setName(mLauncherBinding.userInfoEtName.getText().toString());

                    try {
                        user.setAge(Integer.parseInt(mLauncherBinding.userInfoEtAge.getText().toString()));
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    int selPosition = mLauncherBinding.userInfoSpGender.getSelectedItemPosition();
                    if(selPosition > 0) {
                        user.setGender(mLauncherBinding.userInfoSpGender.getSelectedItem().toString());
                    }

                    Log.d(TAG, user.toString());

                    mLauncherViewModel.addNewUser(user);
                }
        );
    }

    private void fetchTopPerformers() {
        mLauncherViewModel.getTopPerformers().observe(this,
                topPerformers -> {
                    if(topPerformers.size() > 0) {
                        mLauncherBinding.userInfoTvPerformerLabel.setVisibility(View.VISIBLE);
                    } else {
                        mLauncherBinding.userInfoTvPerformerLabel.setVisibility(View.INVISIBLE);
                    }

                    mTopPerformerAdapter.updateUserList(topPerformers);
                    Log.d(TAG, "Performers List Size --> " + topPerformers.size());
                }
        );
    }

    @Override
    public void onGetNoOfQuestions(int noOfQuestions) {
        Log.d(TAG, "No of Questions -> " + noOfQuestions);

        if(noOfQuestions == 0) {
            String questionsJson = AppUtils.readStringFromAsset(this,
                    "questions_and_answers.json");

            if(!TextUtils.isEmpty(questionsJson)) {
                QuestionList allQuestionList = mGson.fromJson(questionsJson, QuestionList.class);

                List<Question> questions = allQuestionList.getQuestions();
                if (null != questions && questions.size() > 0) {
                    mLauncherViewModel.addQuestions(questions);
                }
            }
        }
    }

    @Override
    public void onUserNameEmpty() {
        setInputLayoutError(mLauncherBinding.userInfoIlName, R.string.error_name_required);
    }

    @Override
    public void onUserNameSmall() {
        setInputLayoutError(mLauncherBinding.userInfoIlName, R.string.error_name_size);
    }

    @Override
    public void onAgeSmall() {
        setInputLayoutError(mLauncherBinding.userInfoIlAge, R.string.error_age_range);
    }

    @Override
    public void onGenderEmpty() {
        showToastMessage(R.string.error_gender_required);
    }

    @Override
    public void onUserNameNotUnique() {
        setInputLayoutError(mLauncherBinding.userInfoIlName, R.string.error_name_not_unique);
    }

    @Override
    public void onUserCreatedSuccess(User user) {
        resetInputsFields();

        showToastMessage(getString(R.string.user_created));

        openGamePlay(user);
    }

    private void setInputLayoutError(TextInputLayout textInputLayout, int errorMsgRes) {
        textInputLayout.setErrorEnabled(true);
        textInputLayout.setError(getString(errorMsgRes));
        textInputLayout.requestFocus();
    }

    private void openGamePlay(User user) {
        Intent intent = new Intent(this, GamePlayActivity.class);
        intent.putExtra(AppConstants.USER_DATA_KEY, Parcels.wrap(user));
        startActivity(intent);
    }

    private void resetInputsFields() {
        mLauncherBinding.userInfoEtName.setText("");
        mLauncherBinding.userInfoEtAge.setText("");
        mLauncherBinding.userInfoSpGender.setSelection(0);
    }

    private void dismissKeyboard() {
        AppUtils.dismissKeyboard(this, this.getCurrentFocus());
    }
}