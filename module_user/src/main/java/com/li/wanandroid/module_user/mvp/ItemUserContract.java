package com.li.wanandroid.module_user.mvp;

import com.li.wanandroid.module_user.bean.ItemUserBean;

import wanandroid.li.com.common_base.base.BasePresenter;
import wanandroid.li.com.common_base.base.BaseView;

/**
 * Created by liguangze on 2019/3/20.
 */

public interface ItemUserContract {


    interface View extends BaseView {

        void itemUserData(ItemUserBean itemUserBeans);


    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void itemUserData(int page,int cid);
    }
}
