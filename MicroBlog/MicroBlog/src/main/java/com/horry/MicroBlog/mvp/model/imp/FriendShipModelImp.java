package com.horry.MicroBlog.mvp.model.imp;

import android.content.Context;

import com.google.gson.Gson;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.net.RequestListener;
import com.horry.MicroBlog.api.FriendshipsAPI;
import com.horry.MicroBlog.entity.User;
import com.horry.MicroBlog.entity.list.UserList;
import com.horry.MicroBlog.mvp.model.FriendShipModel;
import com.horry.MicroBlog.ui.common.NewFeature;
import com.horry.MicroBlog.ui.common.login.AccessTokenKeeper;
import com.horry.MicroBlog.ui.common.login.Constants;
import com.horry.MicroBlog.utils.SDCardUtil;
import com.horry.MicroBlog.utils.ToastUtil;

import java.util.ArrayList;

/**
 * Created by wenmingvs on 16/6/6.
 */
public class FriendShipModelImp implements FriendShipModel {

    private OnRequestListener mOnRequestUIListener;
    private Context mContext;

    @Override
    public void user_destroy(final User user, Context context, OnRequestListener onRequestListener, boolean updateCache) {
        FriendshipsAPI friendshipsAPI = new FriendshipsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnRequestUIListener = onRequestListener;
        friendshipsAPI.destroy(Long.valueOf(user.id), user.screen_name, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ToastUtil.showShort(mContext, "取消关注成功");
                //更新内存
                user.following = false;
                //更新本地缓存
                updateCache(mContext, user);
                NewFeature.refresh_profileLayout = true;
                mOnRequestUIListener.onSuccess();
            }

            @Override
            public void onWeiboException(WeiboException e) {
                ToastUtil.showShort(mContext, "取消关注失败");
                ToastUtil.showShort(mContext, e.getMessage());
                mOnRequestUIListener.onError(e.getMessage());
            }
        });
    }

    @Override
    public void user_create(final User user, Context context, OnRequestListener onRequestListener, boolean updateCache) {
        FriendshipsAPI friendshipsAPI = new FriendshipsAPI(context, Constants.APP_KEY, AccessTokenKeeper.readAccessToken(context));
        mContext = context;
        mOnRequestUIListener = onRequestListener;
        friendshipsAPI.create(Long.valueOf(user.id), user.screen_name, new RequestListener() {
            @Override
            public void onComplete(String s) {
                ToastUtil.showShort(mContext, "关注成功");
                user.following = true;
                NewFeature.refresh_profileLayout = true;
                mOnRequestUIListener.onSuccess();
            }

            @Override
            public void onWeiboException(WeiboException e) {
                ToastUtil.showShort(mContext, "关注失败");
                ToastUtil.showShort(mContext, e.getMessage());
                mOnRequestUIListener.onError(e.getMessage());
            }
        });
    }

    /**
     * @param context
     */
    private void updateCache(Context context, User usertoUpdate) {
        String follerResponse = SDCardUtil.get(context, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的粉丝列表" + AccessTokenKeeper.readAccessToken(context).getUid() + ".txt");
        if (follerResponse != null) {
            UserList userList = UserList.parse(follerResponse);
            ArrayList<User> usersList = userList.users;
            for (User user : usersList) {
                if (user.id.equals(usertoUpdate.id)) {
                    user.following = usertoUpdate.following;
                    break;
                }
            }
            userList.users = usersList;
            SDCardUtil.put(context, SDCardUtil.getSDCardPath() + "/weiSwift/profile", "我的粉丝列表" + AccessTokenKeeper.readAccessToken(context).getUid() + ".txt", new Gson().toJson(userList));

        }


    }
}
