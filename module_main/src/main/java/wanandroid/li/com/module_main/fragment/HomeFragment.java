package wanandroid.li.com.module_main.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.li.wanandroid.module_main.R;
import com.li.wanandroid.module_main.R2;
import com.youth.banner.Banner;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.MultiItemTypeAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import wanandroid.li.com.common_base.base.BaseMvpFragment;
import wanandroid.li.com.common_base.constant.ARouterConfig;
import wanandroid.li.com.common_base.constant.Constant;
import wanandroid.li.com.common_base.error.ExceptionHandle;
import wanandroid.li.com.common_base.utils.L;
import wanandroid.li.com.module_main.activity.WebViewActivity;
import wanandroid.li.com.module_main.bean.BannerData;
import wanandroid.li.com.module_main.bean.HomeDataBean;
import wanandroid.li.com.module_main.image.GlideImageLoader;
import wanandroid.li.com.module_main.mvp.home.HomeContract;
import wanandroid.li.com.module_main.mvp.home.HomePresenter;

/**
 * Created by liguangze on 2019/3/12.
 */
@Route(path = ARouterConfig.WAN_MAIN_FRAGMENT)
public class HomeFragment extends BaseMvpFragment<HomeContract.Presenter> implements HomeContract.View{


    @BindView(R2.id.banner)
    Banner mBanner;
    @BindView(R2.id.swipeRefreshLayout)
    TwinklingRefreshLayout mSwipeRefreshLayout;
    @BindView(R2.id.recyclerView)
    RecyclerView mRecyclerView;

    private List<String> mList = new ArrayList<>();
    private List<HomeDataBean.DatasBean> mData = new ArrayList<>();
    private CommonAdapter<HomeDataBean.DatasBean> mAdapter ;
    private int page = Constant.PAGE;

    @Override
    public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

        L.i("throwable = "+throwable.msg);
    }

    @Override
    protected HomeContract.Presenter createPresenter() {
        return new HomePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_home;
    }

    @Override
    protected void initView() {
        initSwipeRefreshLayout();
        presenter.bannerData();
        presenter.homeListData(page);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new CommonAdapter<HomeDataBean.DatasBean>(mContext,R.layout.fragment_home_item,mData) {
            @Override
            protected void convert(ViewHolder holder, HomeDataBean.DatasBean homeDataBean, int position) {

               TextView tv_new = holder.getView(R.id.tv_new);
                boolean fresh = homeDataBean.isFresh();
                if (fresh){
                    tv_new.setVisibility(View.VISIBLE);
                }else {
                    tv_new.setVisibility(View.GONE);
                }


                holder.setText(R.id.home_content,homeDataBean.getTitle());
                holder.setText(R.id.home_name,homeDataBean.getAuthor());
                holder.setText(R.id.home_time,homeDataBean.getNiceDate());
                holder.setText(R.id.tv_type,homeDataBean.getChapterName());

            }
        };
        mRecyclerView.setAdapter(mAdapter);

        mSwipeRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                //下拉刷新指请求第一页的十条数据
                mData.clear();
                page = Constant.PAGE;
                presenter.homeListData(page);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                //上拉加载请求page++页的十条数据
                page++;
                presenter.homeListData(page);
            }
        });


        mAdapter.setOnItemClickListener(new MultiItemTypeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, RecyclerView.ViewHolder holder, int position) {

                String link = mData.get(position).getLink();
                WebViewActivity.actionStart(mContext,link);

            }

            @Override
            public boolean onItemLongClick(View view, RecyclerView.ViewHolder holder, int position) {
                return false;
            }
        });
    }

    @Override
    public void bannerData(List<BannerData> bannerData) {
        finishRefreshLayout();
        for (int i = 0; i < bannerData.size(); i++) {
            mList.add(bannerData.get(i).getImagePath());
        }

        mBanner.setImageLoader(new GlideImageLoader());
        //设置图片集合
        mBanner.setImages(mList);
        //banner设置方法全部调用完毕时最后调用
        mBanner.start();
        dismissLoading();
    }

    @Override
    public void homeListData(HomeDataBean homeDataBeans) {
        List<HomeDataBean.DatasBean> homeDataBeansDatas = homeDataBeans.getDatas();
        mData.addAll(homeDataBeansDatas);
        mAdapter.notifyDataSetChanged();
        finishRefreshLayout();

    }

    protected void initSwipeRefreshLayout(){
        if (null!= mSwipeRefreshLayout){
            mSwipeRefreshLayout.setFloatRefresh(true);

            ProgressLayout header = new ProgressLayout(mContext);
            header.setColorSchemeResources(R.color.black);
            mSwipeRefreshLayout.setHeaderView(header);
            mSwipeRefreshLayout.setEnableLoadmore(true);
        }
    }

    private void finishRefreshLayout(){
        if (mSwipeRefreshLayout != null) {
            mSwipeRefreshLayout.finishRefreshing();
            mSwipeRefreshLayout.finishLoadmore();
        }
    }

}
