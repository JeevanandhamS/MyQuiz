package com.jeeva.myquiz.ui.launcher;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.jeeva.myquiz.R;
import com.jeeva.myquiz.data.dto.User;
import com.jeeva.myquiz.databinding.ActivityLauncherBinding;
import com.jeeva.myquiz.ui.base.BaseActivity;

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
    LauncherViewModel mLauncherViewModel;

    private ActivityLauncherBinding mLauncherBinding;

    private TopPerformerAdapter mTopPerformerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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
    public void onUserNameEmpty() {
        mLauncherBinding.userInfoIlName.setErrorEnabled(true);
        mLauncherBinding.userInfoIlName.setError(getString(R.string.error_name_required));
    }

    @Override
    public void onUserNameSmall() {
        mLauncherBinding.userInfoIlName.setErrorEnabled(true);
        mLauncherBinding.userInfoIlName.setError(getString(R.string.error_name_size));
    }

    @Override
    public void onAgeSmall() {
        mLauncherBinding.userInfoIlAge.setErrorEnabled(true);
        mLauncherBinding.userInfoIlAge.setError(getString(R.string.error_age_range));
    }

    @Override
    public void onGenderEmpty() {
        showToastMessage(R.string.error_gender_required);
    }

    @Override
    public void onUserNameNotUnique() {
        mLauncherBinding.userInfoIlName.setErrorEnabled(true);
        mLauncherBinding.userInfoIlName.setError(getString(R.string.error_name_not_unique));
    }

    @Override
    public void onUserCreatedSuccess(long userId) {
        showToastMessage(String.format(getString(R.string.user_created), userId));
    }

    private void showToastMessage(int msgResId) {
        Toast.makeText(this, msgResId, Toast.LENGTH_SHORT).show();
    }

    private void showToastMessage(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void dismissKeyboard() {
        View view = this.getCurrentFocus();
        if (null != view) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            if (imm != null) {
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        }
    }
}