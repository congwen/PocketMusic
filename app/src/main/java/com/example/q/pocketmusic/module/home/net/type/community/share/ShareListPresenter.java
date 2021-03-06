package com.example.q.pocketmusic.module.home.net.type.community.share;

import android.content.Intent;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.constant.Constant;
import com.example.q.pocketmusic.data.bean.MyUser;
import com.example.q.pocketmusic.data.bean.Song;
import com.example.q.pocketmusic.data.bean.SongObject;
import com.example.q.pocketmusic.data.bean.share.ShareSong;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.data.model.UserShareModel;
import com.example.q.pocketmusic.module.song.SongActivity;
import com.example.q.pocketmusic.module.home.profile.user.other.OtherProfileActivity;


import java.util.List;

/**
 * Created by 鹏君 on 2017/5/16.
 */

public class ShareListPresenter extends BasePresenter<ShareListPresenter.IView> {
    private UserShareModel model;
    private int mPage;
    private int typeId;

    public ShareListPresenter(IView fragment) {
        super(fragment);
        model = new UserShareModel();
    }

    public void getMoreShareList() {
        mPage++;
        model.getAllShareList(typeId, mPage, new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                mView.setList(false, list);
            }
        });

    }

    public void getShareList(final boolean isRefreshing) {
        if (isRefreshing) {
            mPage = 0;
        }
        model.getAllShareList(typeId, mPage, new ToastQueryListener<ShareSong>() {
            @Override
            public void onSuccess(List<ShareSong> list) {
                mView.setList(isRefreshing, list);
            }
        });
    }

    public void setSharePage(int page) {
        this.mPage = page;
    }

    //通过分享乐曲item进入SongActivity
    public void enterSongActivityByShare(ShareSong shareSong) {
        Song song = new Song();
        song.setContent(shareSong.getContent());
        song.setName(shareSong.getName());
        SongObject songObject = new SongObject(song, Constant.FROM_SHARE, Constant.MENU_DOWNLOAD_COLLECTION_AGREE_SHARE, Constant.NET);
        mContext.startActivity(SongActivity.buildShareIntent(
               mContext, songObject, typeId, shareSong
        ));
    }

    public void enterOtherProfileActivity(MyUser other) {
        Intent intent = new Intent(mContext, OtherProfileActivity.class);
        intent.putExtra(OtherProfileActivity.PARAM_USER, other);
        mContext.startActivity(intent);
    }

    public void setType(int typeId) {
        this.typeId = typeId;
    }

    interface IView extends IBaseView {

        void setList(boolean isRefreshing, List<ShareSong> shareSongCache);
    }
}
