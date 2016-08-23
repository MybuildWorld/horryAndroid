package com.example.horry.footbasket.utils;

/**
 * Created by Administrator on 2016/8/14.
 */
public interface RequestCallback<T> {
    void onSuccess(T t);

    void onFailure(String message);
}
