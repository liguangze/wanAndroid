package wanandroid.li.com.module_navigation.mvp;

import java.util.List;

import wanandroid.li.com.common_base.base.BasePresenter;
import wanandroid.li.com.common_base.base.BaseView;
import wanandroid.li.com.module_navigation.bean.NavigationBean;

/**
 * Created by liguangze on 2019/3/19.
 */

public interface NavigationContract {


    interface View extends BaseView {

        void NavigationData(List<NavigationBean> data);


    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void NavigationData();

    }
}
