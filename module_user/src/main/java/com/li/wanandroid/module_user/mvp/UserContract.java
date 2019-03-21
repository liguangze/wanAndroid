package com.li.wanandroid.module_user.mvp;

import com.li.wanandroid.module_user.bean.UserBean;

import java.util.List;

import wanandroid.li.com.common_base.base.BasePresenter;
import wanandroid.li.com.common_base.base.BaseView;

/**
 * Created by liguangze on 2019/3/20.
 */

public interface UserContract {


    interface View extends BaseView {

        void userData(List<UserBean> bannerData);


    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void userData();
    }
}
