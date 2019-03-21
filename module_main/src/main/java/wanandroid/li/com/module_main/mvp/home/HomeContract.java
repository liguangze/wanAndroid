package wanandroid.li.com.module_main.mvp.home;

import java.util.List;

import wanandroid.li.com.common_base.base.BasePresenter;
import wanandroid.li.com.common_base.base.BaseView;
import wanandroid.li.com.module_main.bean.BannerData;
import wanandroid.li.com.module_main.bean.HomeDataBean;

/**
 * Created by liguangze on 2019/3/12.
 */

public interface HomeContract {

    interface View extends BaseView {

        void bannerData(List<BannerData> bannerData);

        void homeListData (HomeDataBean homeDataBeans);

    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void bannerData();
        public abstract void homeListData(int page);
    }
}
