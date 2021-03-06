package com.example.q.pocketmusic.module.common;

import com.example.q.pocketmusic.callback.ToastQueryListener;
import com.example.q.pocketmusic.config.constant.BmobConstant;

import cn.bmob.v3.BmobQuery;

/**
 * Created by 鹏君 on 2017/7/30.
 * （￣m￣）
 */

public class BaseModel<T> {
    public String DEFAULT_INVERTED_CREATE = BmobConstant.BMOB_CREATE_AT;//默认逆序
    public int DEFAULT_LIMIT = 10;

    public void initDefaultListQuery(BmobQuery<?> query) {
        query.order(DEFAULT_INVERTED_CREATE);
    }

    public void initDefaultListQuery(BmobQuery<?> query, int mPage) {
        initDefaultListQuery(query);
        query.setLimit(DEFAULT_LIMIT);
        query.setSkip(mPage * DEFAULT_LIMIT);
    }


    public void getList(int page, ToastQueryListener<T> listener) {
        //可选
    }

    public void getList(Object equalsObject, int page, ToastQueryListener<T> listener) {
        //可选
    }


}
