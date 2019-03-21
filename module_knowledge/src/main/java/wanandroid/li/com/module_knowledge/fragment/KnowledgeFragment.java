package wanandroid.li.com.module_knowledge.fragment;

import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.zhy.adapter.recyclerview.CommonAdapter;
import com.zhy.adapter.recyclerview.base.ViewHolder;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import wanandroid.li.com.common_base.base.BaseMvpFragment;
import wanandroid.li.com.common_base.constant.ARouterConfig;
import wanandroid.li.com.common_base.error.ExceptionHandle;
import wanandroid.li.com.common_base.utils.L;
import wanandroid.li.com.module_knowledge.R;
import wanandroid.li.com.module_knowledge.R2;
import wanandroid.li.com.module_knowledge.fragment.bean.KnowledgeBean;
import wanandroid.li.com.module_knowledge.fragment.mvp.knowledge.KnowledgeContract;
import wanandroid.li.com.module_knowledge.fragment.mvp.knowledge.KnowledgePresenter;

/**
 * Created by liguangze on 2019/3/12.
 */
@Route(path = ARouterConfig.WAN_knowlrdge_FRAGMENT)
public class KnowledgeFragment extends BaseMvpFragment<KnowledgeContract.Presenter> implements KnowledgeContract.View {


    @BindView(R2.id.knowledge_recycle)
    RecyclerView mRecyclerView;
    private List<KnowledgeBean> mData = new ArrayList<>();
    private CommonAdapter<KnowledgeBean> mCommonAdapter ;

    @Override
    public void onShowError(ExceptionHandle.ResponseThrowable throwable) {

    }

    @Override
    protected KnowledgeContract.Presenter createPresenter() {
        return new KnowledgePresenter();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_knowledge;
    }


    @Override
    protected void initView() {
        presenter.KnowledgeData();
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mCommonAdapter = new CommonAdapter<KnowledgeBean>(mContext,R.layout.item_fragment_knowledge,mData) {
            @Override
            protected void convert(ViewHolder holder, KnowledgeBean knowledgeBean, int position) {
                holder.setText(R.id.tv_name,knowledgeBean.getName());

               TextView tvType =  holder.getView(R.id.tv_type);
                String newName = "";
                List<KnowledgeBean.ChildrenBean> children = knowledgeBean.getChildren();

                for (int i = 0; i < children.size(); i++) {
                    newName += children.get(i).getName()+"  ";
                }

                tvType.setText(newName);
            }
        };
        mRecyclerView.setAdapter(mCommonAdapter);


    }

    @Override
    public void KnowledgeData(List<KnowledgeBean> data) {
        L.i("tag","data");

        dismissLoading();
        mData.addAll(data);
        mCommonAdapter.notifyDataSetChanged();


    }
}
