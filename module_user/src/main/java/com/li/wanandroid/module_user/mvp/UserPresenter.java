package com.li.wanandroid.module_user.mvp;

import com.li.wanandroid.module_user.bean.UserBean;
import com.li.wanandroid.module_user.service.UserService;

import java.util.List;

import wanandroid.li.com.common_base.base.BaseObserver;
import wanandroid.li.com.common_base.http.RetrofitClient;
import wanandroid.li.com.common_base.scheduler.RxScheduler;

/**
 * Created by liguangze on 2019/3/20.
 */

public class UserPresenter extends UserContract.Presenter {


    private UserService mUserService;

    public UserPresenter() {
        mUserService = RetrofitClient.getInstance().createApi(UserService.class);

    }

    @Override
    public void userData() {
        view.showLoading();
        mUserService.getUserData()
                .compose(new RxScheduler.compose<List<UserBean>>())
                .subscribe(new BaseObserver<List<UserBean>>(rxManager, view) {
                    @Override
                    public void onNext(List<UserBean> userBeans) {

                        view.userData(userBeans);
                    }
                });
    }
}
