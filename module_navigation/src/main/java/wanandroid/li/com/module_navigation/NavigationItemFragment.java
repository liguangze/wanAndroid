package wanandroid.li.com.module_navigation;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.GridView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhy.adapter.abslistview.CommonAdapter;
import com.zhy.adapter.abslistview.ViewHolder;

import java.util.List;
import java.util.Random;

import butterknife.BindView;
import wanandroid.li.com.common_base.base.BaseMvpFragment;
import wanandroid.li.com.common_base.constant.ARouterConfig;
import wanandroid.li.com.common_base.error.ExceptionHandle;
import wanandroid.li.com.module_navigation.bean.NavigationBean;
import wanandroid.li.com.module_navigation.mvp.NavigationContract;

/**
 * Created by liguangze on 2019/3/12.
 */
@SuppressLint("ValidFragment")
@Route(path = ARouterConfig.WAN_navigation_FRAGMENT)
public class NavigationItemFragment extends BaseMvpFragment {


    @BindView(R2.id.gridview)
    GridView mGridView;

    private CommonAdapter<NavigationBean.ArticlesBean> mAdapter ;

    private List<NavigationBean.ArticlesBean> mList;
    public NavigationItemFragment(List<NavigationBean.ArticlesBean> dataList) {
        this.mList = dataList;
    }

    @Override
    public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

    }

    @Override
    protected NavigationContract.Presenter createPresenter() {
        return null;
    }

    @Override
    protected int getLayoutId() {
        return R.layout.item_fragment_navigation;
    }

    @Override
    protected void initView() {

        mAdapter = new CommonAdapter<NavigationBean.ArticlesBean>(mContext,R.layout.item_grid_navigation,mList) {
            @Override
            protected void convert(ViewHolder viewHolder, NavigationBean.ArticlesBean item, int position) {

               TextView tvName =  viewHolder.getView(R.id.tv_name);
                tvName.setText(item.getTitle());
                tvName.setTextColor(randomColor());

            }
        };

        mGridView.setAdapter(mAdapter);


    }




    //获取随机rgb颜色值
    public  int randomColor(){
        Random random = new Random();
        int red =random.nextInt(150);//0-190        ,如果颜色值过大,就越接近白色,就看不清了,所以需要限定范围
        int green =random.nextInt(150);//0-190
        int blue =random.nextInt(150);//0-190
        return Color.rgb(red,green, blue);//使用rgb混合生成一种新的颜色,Color.rgb生成的是一个int数
    }

}
