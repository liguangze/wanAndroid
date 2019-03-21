package wanandroid.li.com.common_base.application;

import android.annotation.TargetApi;
import android.app.Application;
import android.os.Build;
import android.support.multidex.MultiDex;

import com.alibaba.android.arouter.launcher.ARouter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.footer.LoadingView;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;

import okhttp3.OkHttpClient;
import wanandroid.li.com.common_base.BuildConfig;
import wanandroid.li.com.common_base.base.RouteManager;
import wanandroid.li.com.common_base.base.RouteOptions;
import wanandroid.li.com.common_base.baseBean.BaseData;
import wanandroid.li.com.common_base.baseBean.ErrorResponse;
import wanandroid.li.com.common_base.baseBean.RefreshToken;
import wanandroid.li.com.common_base.http.RetrofitClient;
import wanandroid.li.com.common_base.http.RetrofitConfig;
import wanandroid.li.com.common_base.http.conver.LoggingInterceptor;
import wanandroid.li.com.common_base.http.cookie.CookiesManager;
import wanandroid.li.com.common_base.manager.ActivityManage;
import wanandroid.li.com.common_base.utils.LogUtils;
import wanandroid.li.com.common_base.utils.SpUtils;

/**
 * Created by liguangze on 2019/3/11.
 */

public class BaseApplication extends Application {


    //全局唯一的context
    private static BaseApplication application;
    //Activity管理器
    private ActivityManage activityManage;

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        activityManage = new ActivityManage();
        initARouter();
        initOkhttp();


        TwinklingRefreshLayout.setDefaultHeader(ProgressLayout.class.getName());
        TwinklingRefreshLayout.setDefaultFooter(LoadingView.class.getName());

        //MultiDex分包方法 必须最先初始化
        MultiDex.install(this);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    private void initOkhttp() {

        //实例化Config对象  设置Retrofit baseUrl             https://www.wanandroid.com/
        RetrofitConfig config = new RetrofitConfig("http://www.wanandroid.com/");
//		RetrofitConfig config = new RetrofitConfig("http://hb5.api.okayapi.com/");
        //设置自定义的Okhttpclient 可选
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.cookieJar(new CookiesManager(this));

        builder.addInterceptor(new LoggingInterceptor());//添加日志拦截器

        config.setBuilder(builder);
        //设置异常解析类  可选
        config.setErrorResponse(new ErrorResponse());
        //设置刷新token类  可选
        config.setiRefreshToken(new RefreshToken());
        //设置解析父类 必选
        config.setGsonClass(BaseData.class);
        //设置网络请求时弹出的dialog 可选
//        config.setLodingView(new LoadingView());

        //初始化
        RetrofitClient.init(config);
//        ToastUtils.init(this);
        SpUtils.init(this);
        new LogUtils.LogUtilsBuilder().setPrintClass(false).setPrintLine(false);
//		if (LeakCanary.isInAnalyzerProcess(this)) {
//			// This process is dedicated to LeakCanary for heap analysis.
//			// You should not init your app in this process.
//			return;
//		}
//		LeakCanary.install(this);
        //使用默认配置
        RouteManager.init(this);

        //使用自定义配置
        RouteOptions options = new RouteOptions();
        //设置view点击的过滤时间为2秒
        options.setFilterClickTime(2);
        //设置全局沉浸式状态栏
        options.setTransStatus(true);
        //设置在沉浸式状态栏下布局内容在状态栏之下
        options.setStatusPaddingTop(true);
        RouteManager.init(this,options);
    }

    private void initARouter() {
        if (BuildConfig.DEBUG) {
            ARouter.openLog();  // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application);// 尽可能早，推荐在Application中初始化

    }

    /**
     * 获取全局唯一上下文
     *
     * @return BaseApplication
     */
    public static BaseApplication getApplication() {
        return application;
    }


    /**
     * 退出应用
     */
    public void exitApp() {
        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(0);
    }


    /**
     * 返回Activity管理器
     */
    public ActivityManage getActivityManage() {
        if (activityManage == null) {
            activityManage = new ActivityManage();
        }
        return activityManage;
    }
}
