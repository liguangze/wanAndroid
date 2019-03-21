package wanandroid.li.com.module_knowledge.fragment.mvp.knowledge;

import java.util.List;

import wanandroid.li.com.common_base.base.BasePresenter;
import wanandroid.li.com.common_base.base.BaseView;
import wanandroid.li.com.module_knowledge.fragment.bean.KnowledgeBean;

/**
 * Created by liguangze on 2019/3/18.
 */

public interface KnowledgeContract {


    interface View extends BaseView {

        void KnowledgeData(List<KnowledgeBean> data);


    }

    abstract class Presenter extends BasePresenter<View> {
        public abstract void KnowledgeData();

    }
}
