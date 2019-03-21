package wanandroid.li.com.module_navigation.service;

import java.util.List;

import io.reactivex.Observable;
import retrofit2.http.GET;
import wanandroid.li.com.module_navigation.bean.NavigationBean;

/**
 * Created by liguangze on 2019/3/19.
 */

public interface NavigationService {
    @GET("/navi/json")
    Observable<List<NavigationBean>> getKnowledgeData();
}
