package wanandroid.li.com.module_navigation;

import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.BindView;
import wanandroid.li.com.common_base.base.BaseMvpFragment;
import wanandroid.li.com.common_base.constant.ARouterConfig;
import wanandroid.li.com.common_base.error.ExceptionHandle;
import wanandroid.li.com.module_navigation.adapter.TabFragmentPagerAdapter;
import wanandroid.li.com.module_navigation.adapter.VerticalViewPagerAdapter;
import wanandroid.li.com.module_navigation.bean.NavigationBean;
import wanandroid.li.com.module_navigation.customview.TabView;
import wanandroid.li.com.module_navigation.customview.VerticalTabLayout;
import wanandroid.li.com.module_navigation.customview.ViewPagerSlide;
import wanandroid.li.com.module_navigation.mvp.NavigationContract;
import wanandroid.li.com.module_navigation.mvp.NavigationPresenter;

/**
 * Created by liguangze on 2019/3/12.
 */
@Route(path = ARouterConfig.WAN_navigation_FRAGMENT)
public class NavigationFragment extends BaseMvpFragment<NavigationContract.Presenter> implements NavigationContract.View {


    @BindView(R2.id.tablayout)
    VerticalTabLayout mTabLayout;

    @BindView(R2.id.verticalviewpager)
    ViewPagerSlide mPagerSlide;
    private List<String> tabtitleList = new ArrayList<>();
    private List<Fragment> fragmentList = new ArrayList<>();
    private List<NavigationBean.ArticlesBean> dataList = new ArrayList<>();

    @Override
    public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

    }

    @Override
    protected NavigationContract.Presenter createPresenter() {
        return new NavigationPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_navigation;
    }

    @Override
    protected void initView() {
        presenter.NavigationData();

    }

    @Override
    public void NavigationData(List<NavigationBean> data) {
        dismissLoading();

        if (data != null && data.size() > 0) {
            for (int i = 0; i < data.size(); i++) {
                tabtitleList.add(data.get(i).getName());
                dataList.addAll(data.get(i).getArticles());
            }
        }


        for (int i = 0; i < tabtitleList.size(); i++) {
            fragmentList.add(new NavigationItemFragment(data.get(i).getArticles()));
        }



        if (mTabLayout != null && mPagerSlide != null) {
            mTabLayout.setTabAdapter(new VerticalViewPagerAdapter(getActivity(), tabtitleList));
            mPagerSlide.setOffscreenPageLimit(fragmentList.size() - 1);
            mPagerSlide.setAdapter(new TabFragmentPagerAdapter(getActivity().getSupportFragmentManager(), fragmentList, tabtitleList));
            mPagerSlide.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
                @Override
                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

                }

                @Override
                public void onPageSelected(int position) {
                    mTabLayout.setTabSelected(position);
                }

                @Override
                public void onPageScrollStateChanged(int state) {

                }
            });


            mTabLayout.addOnTabSelectedListener(new VerticalTabLayout.OnTabSelectedListener() {
                @Override
                public void onTabSelected(TabView tab, int position) {
                    mPagerSlide.setCurrentItem(position);

                }

                @Override
                public void onTabReselected(TabView tab, int position) {

                }
            });
        }





    }


}
