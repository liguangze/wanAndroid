package com.li.wanandroid.module_user.service;

import com.li.wanandroid.module_user.bean.ItemUserBean;
import com.li.wanandroid.module_user.bean.UserBean;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

/**
 * Created by liguangze on 2019/3/20.
 */

public interface UserService {
    @GET("/project/tree/json")
    Observable<List<UserBean>> getUserData();


    @GET("/project/list/{page}/json")
    Observable<ItemUserBean> getItemUserData(@Path("page") int page , @Query("cid") int cid);
}
