package wanandroid.li.com.module_main.service;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;
import wanandroid.li.com.module_main.bean.BannerData;
import wanandroid.li.com.module_main.bean.HomeDataBean;

/**
 * Created by liguangze on 2019/3/12.
 */

public interface HomeService {

    @GET("banner/json")
    Observable<List<BannerData>> banner();


    @GET("article/list/{page}/json")
    Observable<HomeDataBean> homeListData(@Path("page") int page );

}
