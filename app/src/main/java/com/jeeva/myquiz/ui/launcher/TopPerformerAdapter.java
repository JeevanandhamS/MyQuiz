package com.jeeva.myquiz.ui.launcher;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.jeeva.myquiz.R;
import com.jeeva.myquiz.data.dto.User;
import com.jeeva.myquiz.databinding.InflaterPerformerRowBinding;

import org.threeten.bp.OffsetDateTime;
import org.threeten.bp.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jeevanandham on 12/08/2018
 */
public class TopPerformerAdapter extends RecyclerView.Adapter<TopPerformerAdapter.TopPerformerVH> {

    private LayoutInflater mLayoutInflater;

    private List<User> mUserList = new ArrayList<>();

    public TopPerformerAdapter(LayoutInflater layoutInflater) {
        this.mLayoutInflater = layoutInflater;
    }

    public void updateUserList(List<User> userList) {
        mUserList.clear();
        mUserList.addAll(userList);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TopPerformerVH onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        return new TopPerformerVH(DataBindingUtil.inflate(mLayoutInflater,
                R.layout.inflater_performer_row, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull TopPerformerVH topPerformerVH, int position) {
        topPerformerVH.bind(mUserList.get(position), position + 1);
    }

    @Override
    public int getItemCount() {
        return mUserList.size();
    }

    static class TopPerformerVH extends RecyclerView.ViewHolder {

        InflaterPerformerRowBinding mPerformerRowBinding;

        public TopPerformerVH(InflaterPerformerRowBinding performerRowBinding) {
            super(performerRowBinding.getRoot());
            this.mPerformerRowBinding = performerRowBinding;
        }

        public void bind(User user, int rank) {
            mPerformerRowBinding.performerRowTvName.setText(rank + ". " + user.getName());
            mPerformerRowBinding.performerRowTvPoints.setText(String.valueOf(user.getPoints()));

            OffsetDateTime createdTime = user.getCreatedTime();
            if(null != createdTime) {
                String playedTime = createdTime.format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm"));
                mPerformerRowBinding.performerRowTvPlayedTime.setText(playedTime);
            } else {
                mPerformerRowBinding.performerRowTvPlayedTime.setText("-");
            }
        }
    }
}