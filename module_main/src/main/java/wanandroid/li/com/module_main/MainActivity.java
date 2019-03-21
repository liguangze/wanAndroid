package wanandroid.li.com.module_main;

import android.support.annotation.IdRes;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioGroup;

import com.li.wanandroid.module_main.R;
import com.li.wanandroid.module_main.R2;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import wanandroid.li.com.common_base.application.BaseApplication;
import wanandroid.li.com.common_base.base.BaseFragment;
import wanandroid.li.com.common_base.base.BaseMvpActivity;
import wanandroid.li.com.common_base.base.BasePresenter;
import wanandroid.li.com.common_base.constant.ARouterConfig;
import wanandroid.li.com.common_base.error.ExceptionHandle;
import wanandroid.li.com.common_base.utils.ARouterUtils;
import wanandroid.li.com.common_base.utils.ToastUtils;


public class MainActivity extends BaseMvpActivity {

    @BindView(R2.id.rg_main)
    RadioGroup mainTab;
    @BindView(R2.id.drawer)
    DrawerLayout mDrawerLayout;

    @BindView(R2.id.toolbar_left)
    ImageView mToorLeft;



    private long mExitTime;
    /**
     * 存放切换Fragment
     */
    private List<Fragment> mFragmentList = new ArrayList<>();

    //玩android模块Fragment
    private BaseFragment wanFragment = ARouterUtils.getFragment(ARouterConfig.WAN_MAIN_FRAGMENT);
    //知识体系模块Fragment
    private BaseFragment knowlrdgeFragment = ARouterUtils.getFragment(ARouterConfig.WAN_knowlrdge_FRAGMENT);
    //导航模块Fragment
    private BaseFragment navigationFragment = ARouterUtils.getFragment(ARouterConfig.WAN_navigation_FRAGMENT);
    //项目Fragment
    private BaseFragment userFragment = ARouterUtils.getFragment(ARouterConfig.USER_MAIN_FRAGMENT);
    private View mRootView;

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }



    @Override
    protected void initView() {
        changeFragment(ARouterConfig.WAN_MAIN_FRAGMENT);
        mainTab.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, @IdRes int checkedId) {
                if (checkedId == R.id.rb_main) {
                    changeFragment(ARouterConfig.WAN_MAIN_FRAGMENT);
                }else if (checkedId == R.id.rb_knowledge) {
                    changeFragment(ARouterConfig.WAN_knowlrdge_FRAGMENT);
                }else if (checkedId == R.id.rb_navigation) {
                    changeFragment(ARouterConfig.WAN_navigation_FRAGMENT);
                }else if (checkedId == R.id.rb_user) {
                    changeFragment(ARouterConfig.USER_MAIN_FRAGMENT);
                }
                else {
                    changeFragment(ARouterConfig.WAN_MAIN_FRAGMENT);
                }

            }
        });  //检查文件权限



        mToorLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                mDrawerLayout.openDrawer(Gravity.START);

            }
        });

    }



    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 2000) {
                ToastUtils.showToast(mContext, getString(R.string.main_exit_app));
                mExitTime = System.currentTimeMillis();
            } else {
                BaseApplication.getApplication().exitApp();
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }


    /**
     * Fragment 发生改变
     *
     * @param tag Fragment 类名
     */
    public void changeFragment(String tag) {
        hideFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        Fragment fragment = getSupportFragmentManager().findFragmentByTag(tag);
        if (fragment != null) {
            transaction.show(fragment);
        } else {
            if (TextUtils.equals(tag, ARouterConfig.WAN_MAIN_FRAGMENT)) {
                fragment = wanFragment;
            } else if (TextUtils.equals(tag, ARouterConfig.WAN_knowlrdge_FRAGMENT)) {
                fragment = knowlrdgeFragment;
            }else if (TextUtils.equals(tag, ARouterConfig.WAN_navigation_FRAGMENT)) {
                fragment = navigationFragment;
            }else if (TextUtils.equals(tag, ARouterConfig.USER_MAIN_FRAGMENT)) {
                fragment = userFragment;
            }else {
                fragment = wanFragment;
            }
            mFragmentList.add(fragment);
            transaction.add(R.id.fl_context, fragment, tag);
        }
        transaction.commitAllowingStateLoss();
    }

    /**
     * 隐藏所有Fragment
     */
    private void hideFragment() {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        for (Fragment f : mFragmentList) {
            transaction.hide(f);
        }
        transaction.commit();

    }

    @Override
    protected BasePresenter createPresenter() {
        return null;
    }

    @Override
    public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

    }
}
