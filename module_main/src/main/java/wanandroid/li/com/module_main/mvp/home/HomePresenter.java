package wanandroid.li.com.module_main.mvp.home;

import java.util.List;

import wanandroid.li.com.common_base.base.BaseObserver;
import wanandroid.li.com.common_base.http.RetrofitClient;
import wanandroid.li.com.common_base.scheduler.RxScheduler;
import wanandroid.li.com.module_main.bean.BannerData;
import wanandroid.li.com.module_main.bean.HomeDataBean;
import wanandroid.li.com.module_main.service.HomeService;

/**
 * Created by liguangze on 2019/3/12.
 */

public class HomePresenter extends HomeContract.Presenter {

    HomeService mHomeService;
    public HomePresenter() {
        mHomeService = RetrofitClient.getInstance().createApi(HomeService.class);
    }

    @Override
    public void bannerData() {
        view.showLoading();
        mHomeService.banner()
                .compose(new RxScheduler.compose<List<BannerData>>())
                .subscribe(new BaseObserver<List<BannerData>>(rxManager, view) {
                    @Override
                    public void onNext(List<BannerData> bannerData) {
                        view.bannerData(bannerData);
                    }
                });
    }

    @Override
    public void homeListData(int page) {
        mHomeService.homeListData(page)
                .compose(new RxScheduler.compose<HomeDataBean>())
                .subscribe(new BaseObserver<HomeDataBean>(rxManager, view) {
                    @Override
                    public void onNext(HomeDataBean bannerData) {
                        view.homeListData(bannerData);
                    }
                });
    }
}
