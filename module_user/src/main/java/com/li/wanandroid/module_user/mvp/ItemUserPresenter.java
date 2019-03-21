package com.li.wanandroid.module_user.mvp;

import com.li.wanandroid.module_user.bean.ItemUserBean;
import com.li.wanandroid.module_user.service.UserService;

import wanandroid.li.com.common_base.base.BaseObserver;
import wanandroid.li.com.common_base.http.RetrofitClient;
import wanandroid.li.com.common_base.scheduler.RxScheduler;

/**
 * Created by liguangze on 2019/3/20.
 */

public class ItemUserPresenter extends ItemUserContract.Presenter {


    private UserService mUserService;

    public ItemUserPresenter() {
        mUserService = RetrofitClient.getInstance().createApi(UserService.class);

    }

    @Override
    public void itemUserData(int page ,int cid) {
        mUserService.getItemUserData(page,cid)
                .compose(new RxScheduler.compose<ItemUserBean>())
                .subscribe(new BaseObserver<ItemUserBean>(rxManager, view) {
                    @Override
                    public void onNext(ItemUserBean userBeans) {

                        view.itemUserData(userBeans);

                    }
                });
    }
}
