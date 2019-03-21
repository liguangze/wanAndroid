package com.li.wanandroid.module_user.fragment;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.li.wanandroid.module_user.R;
import com.li.wanandroid.module_user.R2;
import com.li.wanandroid.module_user.bean.UserBean;
import com.li.wanandroid.module_user.mvp.UserContract;
import com.li.wanandroid.module_user.mvp.UserPresenter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import wanandroid.li.com.common_base.adapter.TabFragmentPagerAdapter;
import wanandroid.li.com.common_base.base.BaseMvpFragment;
import wanandroid.li.com.common_base.constant.ARouterConfig;
import wanandroid.li.com.common_base.error.ExceptionHandle;

/**
 * Created by liguangze on 2019/3/12.
 */
@Route(path = ARouterConfig.USER_MAIN_FRAGMENT)
public class UserFragment extends BaseMvpFragment<UserContract.Presenter> implements UserContract.View {

    @BindView(R2.id.tablayout)
    TabLayout mTabLayout;

    @BindView(R2.id.viewpager)
    ViewPager mViewPager;
    private List<String> mList = new ArrayList<>();
    private List<Fragment> list_fragment = new ArrayList<>();//Fragment的集合


    @Override
    public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

    }

    @Override
    protected UserContract.Presenter createPresenter() {
        return new UserPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_user;
    }

    @Override
    protected void initView() {
        presenter.userData();

    }

    @Override
    public void userData(List<UserBean> userData) {

        dismissLoading();
        for (int i = 0; i <userData.size() ; i++) {
            mList.add(userData.get(i).getName());
        }


        for (int i = 0; i < mList.size(); i++) {
            mTabLayout.addTab(mTabLayout.newTab().setText(mList.get(i)));//添加tab选项
            list_fragment.add(new UseritemFragment(userData.get(i).getId()));
        }

        TabFragmentPagerAdapter adapter = new TabFragmentPagerAdapter(
                this.getFragmentManager(), list_fragment, mList
        );
        //viewpager 加载adapter
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }
}

