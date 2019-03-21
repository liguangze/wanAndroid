package wanandroid.li.com.module_knowledge.fragment.mvp.knowledge;

import java.util.List;

import wanandroid.li.com.common_base.base.BaseObserver;
import wanandroid.li.com.common_base.http.RetrofitClient;
import wanandroid.li.com.common_base.scheduler.RxScheduler;
import wanandroid.li.com.module_knowledge.fragment.bean.KnowledgeBean;
import wanandroid.li.com.module_knowledge.fragment.service.KnowledgeService;

/**
 * Created by liguangze on 2019/3/18.
 */

public class KnowledgePresenter extends KnowledgeContract.Presenter{
    KnowledgeService mKnowledgeService;


    public KnowledgePresenter() {
        mKnowledgeService = RetrofitClient.getInstance().createApi(KnowledgeService.class);
    }

    @Override
    public void KnowledgeData() {

        view.showLoading();
        mKnowledgeService.getKnowledgeData()
                .compose(new RxScheduler.compose<List<KnowledgeBean>>())
                .subscribe(new BaseObserver<List<KnowledgeBean>>(rxManager, view) {

                    @Override
                    public void onNext(List<KnowledgeBean> knowledgeBeans) {
                        view.KnowledgeData(knowledgeBeans);

                    }
                });
    }
}
