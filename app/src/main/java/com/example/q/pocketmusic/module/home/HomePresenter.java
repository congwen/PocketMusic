package com.example.q.pocketmusic.module.home;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.example.q.pocketmusic.R;
import com.example.q.pocketmusic.module.common.BasePresenter;
import com.example.q.pocketmusic.module.common.IBaseView;
import com.example.q.pocketmusic.module.home.local.HomeLocalFragment;
import com.example.q.pocketmusic.module.home.net.HomeNetFragment;
import com.example.q.pocketmusic.module.home.profile.HomeProfileFragment;
import com.example.q.pocketmusic.module.home.profile.support.SupportActivity;
import com.example.q.pocketmusic.module.home.search.HomeSearchFragment;
import com.example.q.pocketmusic.util.common.SharedPrefsUtil;
import com.example.q.pocketmusic.util.common.update.UpdateUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by 鹏君 on 2016/11/22.
 */

public class HomePresenter extends BasePresenter<HomePresenter.IView> {

    public interface TabIndex {
        int NET_INDEX=0;
        int SEARCH_INDEX = 1;
        int LOCAL_INDEX = 2;
        int PROFILE_INDEX = 3;
        int INIT_INDEX=-1;//初始值
    }
    private IView activity;
    private List<Fragment> fragments;
    private FragmentManager fm;
    private Fragment totalFragment;
    private HomeNetFragment homeNetFragment;//1
    private HomeSearchFragment homeSearchFragment;//2
    private HomeLocalFragment homeLocalFragment;//3
    private HomeProfileFragment homeProfileFragment;//4
    private int mCurIndex = TabIndex.INIT_INDEX;//标记当前Fragment
    private HashMap<String,Fragment> map;//改用HashMap来取得fragment


    public HomePresenter(IView activity) {
        attachView(activity);
        this.activity = getIViewRef();
        initFragment();
    }

    private void initFragment() {
        fragments = new ArrayList<>();
        homeNetFragment = new HomeNetFragment();
        homeSearchFragment = new HomeSearchFragment();
        homeLocalFragment = new HomeLocalFragment();
        homeProfileFragment = new HomeProfileFragment();
        fragments.add(TabIndex.NET_INDEX,homeNetFragment);
        fragments.add(TabIndex.SEARCH_INDEX,homeSearchFragment);
        fragments.add(TabIndex.LOCAL_INDEX,homeLocalFragment);
        fragments.add(TabIndex.PROFILE_INDEX,homeProfileFragment);
    }

    public void clickBottomTab(int index) {
        if (mCurIndex!=index){
            showFragment(fragments.get(index));
            activity.onSelectTabResult(mCurIndex,index);
            mCurIndex = index;
        }
    }

    private void showFragment(Fragment fragment) {
        if (!fragment.isAdded()) {
            if (totalFragment == null) {
                fm.beginTransaction().add(R.id.home_content, fragment, fragment.getClass().getName()).commit();
            } else {
                fm.beginTransaction().hide(totalFragment).add(R.id.home_content, fragment, fragment.getClass().getName()).commit();
            }
        } else {
            fm.beginTransaction().hide(totalFragment).show(fragment).commit();
        }
        totalFragment = fragment;
    }

    //检查版本更新
    public void checkVersion() {
        UpdateUtils.getInstance().update(activity.getCurrentContext());
    }


    public void setFragmentManager(FragmentManager fragmentManager) {
        this.fm = fragmentManager;
    }

    public void enterSupportActivity() {
        activity.getCurrentContext().startActivity(new Intent(activity.getCurrentContext(), SupportActivity.class));
    }

    //弹出支持开发者的字段
    public void checkAlertSupportDialog() {
        long ago = SharedPrefsUtil.getLong("support_date", 1503071043854L);
        long now = System.currentTimeMillis();
        if (now - ago >= 36 * 1000 * 60 * 60) {
            SharedPrefsUtil.putLong("support_date", now);
            activity.alertSupportDialog();
        }
    }


    public interface IView extends IBaseView {


        void alertSupportDialog();

        void onSelectTabResult(int oldIndex, int index);
    }
}
