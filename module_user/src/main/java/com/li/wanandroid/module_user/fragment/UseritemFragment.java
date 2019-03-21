package com.li.wanandroid.module_user.fragment;

import android.annotation.SuppressLint;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.ImageView;

import com.lcodecore.tkrefreshlayout.RefreshListenerAdapter;
import com.lcodecore.tkrefreshlayout.TwinklingRefreshLayout;
import com.lcodecore.tkrefreshlayout.header.progresslayout.ProgressLayout;
import com.li.wanandroid.module_user.R;
import com.li.wanandroid.module_user.R2;
import com.li.wanandroid.module_user.bean.ItemUserBean;
import com.li.wanandroid.module_user.mvp.ItemUserContract;
import com.li.wanandroid.module_user.mvp.ItemUserPresenter;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import wanandroid.li.com.common_base.base.BaseMvpFragment;
import wanandroid.li.com.common_base.constant.Constant;
import wanandroid.li.com.common_base.error.ExceptionHandle;
import wanandroid.li.com.common_base.utils.ImageLoaderUtils;
import wanandroid.li.com.common_base.utils.L;

/**
 * Created by liguangze on 2019/3/12.
 */
@SuppressLint("ValidFragment")
public class UseritemFragment extends BaseMvpFragment<ItemUserContract.Presenter> implements ItemUserContract.View {


    @BindView(R2.id.swipeRefreshLayout)
    TwinklingRefreshLayout mSwipeRefreshLayout;
    @BindView(R2.id.recycleview)
    RecyclerView mRecyclerView;
    private CommonAdapter<ItemUserBean.DatasBean> mCommonAdapter;
    private List<ItemUserBean.DatasBean> mData = new ArrayList<>();

    private int page = 1;
    private int mCid;
    public UseritemFragment(int id) {
        this.mCid = id;

    }

    @Override
    public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

        L.i("throwable = "+throwable.msg);
    }

    @Override
    protected ItemUserContract.Presenter createPresenter() {
        return new ItemUserPresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_fragment_user;
    }

    @Override
    protected void initView() {
        initSwipeRefreshLayout();
        presenter.itemUserData(Constant.PAGE,mCid);

        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mCommonAdapter = new CommonAdapter<ItemUserBean.DatasBean>(mContext,R.layout.item_item_fragment_user,mData) {
            @Override
            protected void convert(ViewHolder holder, ItemUserBean.DatasBean itemUserBean, int position) {

                holder.setText(R.id.tv_title,itemUserBean.getTitle());
                holder.setText(R.id.tv_content,itemUserBean.getDesc());
                holder.setText(R.id.tv_name,itemUserBean.getAuthor());
                holder.setText(R.id.tv_time,itemUserBean.getNiceDate());

               ImageView iv_image =  holder.getView(R.id.iv_image);
                ImageLoaderUtils.display(mContext,iv_image,itemUserBean.getEnvelopePic());


            }
        };


        mRecyclerView.setAdapter(mCommonAdapter);
        mSwipeRefreshLayout.setOnRefreshListener(new RefreshListenerAdapter() {
            @Override
            public void onRefresh(TwinklingRefreshLayout refreshLayout) {
                //下拉刷新指请求第一页的十条数据
                mData.clear();
                page = 1;
                presenter.itemUserData(page,mCid);
            }

            @Override
            public void onLoadMore(TwinklingRefreshLayout refreshLayout) {
                //上拉加载请求page++页的十条数据
                page++;
                presenter.itemUserData(page,mCid);
            }
        });

    }


    @Override
    public void itemUserData(ItemUserBean itemUserBeans) {
        finishRefreshLayout();
        mData.addAll(itemUserBeans.getDatas());
        mCommonAdapter.notifyDataSetChanged();
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
