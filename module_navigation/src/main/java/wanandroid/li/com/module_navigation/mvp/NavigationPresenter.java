package wanandroid.li.com.module_navigation.mvp;

import java.util.List;

import wanandroid.li.com.common_base.base.BaseObserver;
import wanandroid.li.com.common_base.http.RetrofitClient;
import wanandroid.li.com.common_base.scheduler.RxScheduler;
import wanandroid.li.com.module_navigation.bean.NavigationBean;
import wanandroid.li.com.module_navigation.service.NavigationService;

/**
 * Created by liguangze on 2019/3/19.
 */

public class NavigationPresenter extends NavigationContract.Presenter {

    private NavigationService mNavigationService;
    public NavigationPresenter() {
        mNavigationService = RetrofitClient.getInstance().createApi(NavigationService.class);

    }

    @Override
    public void NavigationData() {
        view.showLoading();
        mNavigationService.getKnowledgeData()
                .compose(new RxScheduler.compose<List<NavigationBean>>())
                .subscribe(new BaseObserver<List<NavigationBean>>(rxManager, view) {
                    @Override
                    public void onNext(List<NavigationBean> navigationBeans) {

                        view.NavigationData(navigationBeans);
                    }
                });
    }
}
