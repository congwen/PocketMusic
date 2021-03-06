package com.example.q.pocketmusic.module.home.profile.suggestion;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.data.bean.bmob.UserSuggestion;
import com.jude.easyrecyclerview.adapter.BaseViewHolder;
import com.jude.easyrecyclerview.adapter.RecyclerArrayAdapter;

/**
 * Created by 鹏君 on 2016/11/15.
 */

public class SuggestionAdapter extends RecyclerArrayAdapter<UserSuggestion> {
    public SuggestionAdapter(Context context) {
        super(context);
    }

    @Override
    public BaseViewHolder OnCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(parent);
    }

    class MyViewHolder extends BaseViewHolder<UserSuggestion> {
        TextView suggestionTv;
        TextView replyTv;
        LinearLayout rightLl;

        public MyViewHolder(ViewGroup parent) {
            super(parent, R.layout.item_suggestion);
            suggestionTv = $(R.id.suggestion_tv);
            replyTv = $(R.id.reply_tv);
            rightLl = $(R.id.right_ll);
        }

        @Override
        public void setData(UserSuggestion data) {
            super.setData(data);
            suggestionTv.setText("我: " + data.getSuggestion());
            if (data.getReply() == null) {
                rightLl.setVisibility(View.GONE);
            } else {
                replyTv.setText("管理员:" + data.getReply());
            }

        }

    }
}
