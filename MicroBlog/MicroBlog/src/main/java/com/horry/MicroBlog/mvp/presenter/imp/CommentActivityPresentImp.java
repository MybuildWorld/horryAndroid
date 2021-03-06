package com.horry.MicroBlog.mvp.presenter.imp;

import android.content.Context;

import com.horry.MicroBlog.api.CommentsAPI;
import com.horry.MicroBlog.entity.Comment;
import com.horry.MicroBlog.mvp.model.CommentModel;
import com.horry.MicroBlog.mvp.model.imp.CommentModelImp;
import com.horry.MicroBlog.mvp.presenter.CommentActivityPresent;
import com.horry.MicroBlog.mvp.view.CommentActivityView;
import com.horry.MicroBlog.ui.common.login.Constants;

import java.util.ArrayList;

/**
 * Created by wenmingvs on 16/5/15.
 */
public class CommentActivityPresentImp implements CommentActivityPresent {
    private CommentModel mCommentModel;
    private CommentActivityView mCommentActivityView;

    public CommentActivityPresentImp(CommentActivityView commentActivityView) {
        this.mCommentActivityView = commentActivityView;
        this.mCommentModel = new CommentModelImp();
    }

    @Override
    public void pullToRefreshData(int groupId, Context context) {
        mCommentActivityView.showLoadingIcon();
        switch (groupId) {
            case Constants.GROUP_COMMENT_TYPE_ALL:
                mCommentModel.toMe(CommentsAPI.AUTHOR_FILTER_ALL, context, pullToRefreshListener);
                break;
            case Constants.GROUP_COMMENT_TYPE_FRIENDS:
                mCommentModel.toMe(CommentsAPI.AUTHOR_FILTER_ATTENTIONS, context, pullToRefreshListener);
                break;
            case Constants.GROUP_COMMENT_TYPE_BYME:
                mCommentModel.byMe(context, pullToRefreshListener);
                break;
        }
    }


    @Override
    public void requestMoreData(int sourceType, Context context) {
        switch (sourceType) {
            case Constants.GROUP_COMMENT_TYPE_ALL:
                mCommentModel.toMeNextPage(CommentsAPI.AUTHOR_FILTER_ALL, context, requestMoreListener);
                break;
            case Constants.GROUP_COMMENT_TYPE_FRIENDS:
                mCommentModel.toMeNextPage(CommentsAPI.AUTHOR_FILTER_ATTENTIONS, context, requestMoreListener);
                break;
            case Constants.GROUP_COMMENT_TYPE_BYME:
                mCommentModel.byMeNextPage(context, requestMoreListener);
                break;

        }
    }


    private CommentModel.OnDataFinishedListener pullToRefreshListener = new CommentModel.OnDataFinishedListener() {
        @Override
        public void noMoreDate() {
            mCommentActivityView.hideLoadingIcon();

        }

        @Override
        public void onDataFinish(ArrayList<Comment> commentlist) {
            mCommentActivityView.hideLoadingIcon();
            mCommentActivityView.scrollToTop(false);
            mCommentActivityView.updateListView(commentlist);
        }

        @Override
        public void onError(String error) {
            mCommentActivityView.hideLoadingIcon();
            mCommentActivityView.showErrorFooterView();
        }
    };

    private CommentModel.OnDataFinishedListener requestMoreListener = new CommentModel.OnDataFinishedListener() {
        @Override
        public void noMoreDate() {
            mCommentActivityView.showEndFooterView();
        }

        @Override
        public void onDataFinish(ArrayList<Comment> commentlist) {
            mCommentActivityView.hideFooterView();
            mCommentActivityView.updateListView(commentlist);
        }

        @Override
        public void onError(String error) {
            mCommentActivityView.showErrorFooterView();
        }
    };
}
